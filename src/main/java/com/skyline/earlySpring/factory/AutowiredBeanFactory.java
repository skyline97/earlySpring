package com.skyline.earlySpring.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.skyline.earlySpring.core.BeanDefinition;
import com.skyline.earlySpring.core.BeanReference;
import com.skyline.earlySpring.core.PropertyValue;

public class AutowiredBeanFactory extends AbstractBeanFactory {

	/**
	 * 实现依赖注入的地方
	 */
	@Override
	protected void applyPropertyValues(Object bean,
			BeanDefinition beanDefinition) throws Exception{
		for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
			Object value = propertyValue.getValue();
			//对应属性是ref的情况
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
				System.out.println(declaredMethod);
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
