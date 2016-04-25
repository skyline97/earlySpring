package com.skyline.tinySpring.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.skyline.tinySpring.core.BeanDefinition;
import com.skyline.tinySpring.core.BeanPostProcessor;

public abstract class AbstractBeanFactory implements BeanFactory{

	/**
	 * 具体的IOC容器
	 */
	private Map<String, BeanDefinition> beanDefinitionMap = 
			new ConcurrentHashMap<String, BeanDefinition>();
	
	private final List<String> beanDefinitionNames = new ArrayList<String>();
	
	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
	
	@Override
	public boolean containsBean(String name) throws Exception {
		return beanDefinitionMap.containsKey(name);
	}
	
	@Override
	public Object getBean(String name) throws Exception {
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if(beanDefinition == null)
			throw new IllegalArgumentException("cant find bean named " + name);
		Object bean = beanDefinition.getBean();
		if(bean == null) {
			bean = doCreateBean(beanDefinition);
			bean = initializeBean(bean,name);
			beanDefinition.setBean(bean);
		}
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> clazz) throws Exception {
		for(String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = beanDefinitionMap.get(beanDefinitionName);
			if(clazz.isAssignableFrom(beanDefinition.getBeanClass()))
				return (T) beanDefinition.getBean();
		}
		
		throw new RuntimeException("find no bean");
	}
	
	@Override
	public <T> T getBean(String name, Class<T> clazz) throws Exception {
		//TODO 
		return null;
	}
	
	@Override
	public boolean isPrototype(String name) throws Exception {
		//TODO
		return false;
	}
	
	@Override
	public boolean isSingleton(String name) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 创建一个Bean实例
	 * @param beanDefinition
	 * @return
	 * @throws Exception
	 */
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception{
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		applyPropertyValues(bean,beanDefinition);
		return bean;
	}
	
	/**
	 * 预实例化单例的Bean
	 * @throws Exception
	 */
	public void preInitiateSingletons() throws Exception {
		for(Iterator<String> it = this.beanDefinitionNames.iterator(); it.hasNext();) {
			String beanName = it.next();
			getBean(beanName);
		}
	}
	
	/**
	 * 模板方法,由具体的BeanDefinition实现
	 * @param bean
	 * @param beanDefinition
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception{
		throw new UnsupportedOperationException();
	}

	/**
	 * 按照beanPostProcessor的定义顺序对Bean进行初始化
	 * @param bean
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private Object initializeBean(Object bean,String name) throws Exception{
		for(BeanPostProcessor processor : beanPostProcessors) {
			bean = processor.postProcessBeforeInitialization(bean, name);
		}
		
		for(BeanPostProcessor processor : beanPostProcessors) {
			bean = processor.postProcessAfterInitialization(bean, name);
		}
		
		return bean;
	}
	
	/**
	 * 使用反射生成具体对象
	 * @param beanDefinition
	 * @return
	 * @throws Exception
	 */
	private Object createBeanInstance(BeanDefinition beanDefinition) throws Exception{
		return beanDefinition.getBeanClass().newInstance();
	}
	
	/**
	 * 向容器中注册BeanDefinition
	 * @param name
	 * @param beanDefinition
	 */
	public void registerBeanDefinition(String name,BeanDefinition beanDefinition) {
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}
	
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.add(beanPostProcessor);
	}
	
	
}
