package com.skyline.tinySpring.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.skyline.tinySpring.core.BeanDefinition;
import com.skyline.tinySpring.core.BeanReference;
import com.skyline.tinySpring.core.PropertyValue;

public class AutowireBeanFactory extends AbstractBeanFactory{

	@Override
	protected void applyPropertyValues(Object bean,
			BeanDefinition beanDefinition) throws Exception{
		for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
			Object value = propertyValue.getValue();
			if(value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
			
			//通过反射获取setXX方法进行属性注入
			Method declaredMethod;
			try {
				declaredMethod = bean.getClass().getDeclaredMethod(
							"set" + propertyValue.getName().substring(0,1).toUpperCase()
							+ propertyValue.getName().substring(1), value.getClass());
				declaredMethod.setAccessible(true);
				declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
				//对应该属性没有set方法的情况
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
			
		}
		
	}
	
}
