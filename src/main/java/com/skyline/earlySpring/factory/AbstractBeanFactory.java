package com.skyline.earlySpring.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.skyline.earlySpring.core.BeanDefinition;
import com.skyline.earlySpring.core.BeanPostProcessor;

public abstract class AbstractBeanFactory implements BeanFactory {

	/**
	 * 具体的IOC容器
	 */
	private Map<String, BeanDefinition> beanDefinitionMap = 
			new ConcurrentHashMap<String, BeanDefinition>();
	
	private final List<String> beanDefinitionNames = new ArrayList<String>();
	
	/**
	 * PostProcessor列表
	 */
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
			//第二次setBean
			beanDefinition.setBean(bean);
		}
		return bean;
	}
	
	/**
	 * 通过遍历beanDefinitionNames来找到相同Class的bean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> clazz) throws Exception {
		for(String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = beanDefinitionMap.get(beanDefinitionName);
			if(clazz.isAssignableFrom(beanDefinition.getBeanClass())) {
				return (T) getBean(beanDefinitionName);
			}
		}
		
		//如果未找到,则抛出异常
		throw new RuntimeException("find no bean");
	}
	
	/**
	 * 首先通过getBean(name),再判断class类型,若正确,返回;若判断失败,抛出异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name, Class<T> clazz) throws Exception {
		Object bean = getBean(name);
		if(bean.getClass().isAssignableFrom(clazz))
			return (T) bean;
		
		//如果未找到,则抛出异常
		throw new RuntimeException("find no bean");
	}
	
	@Override
	public boolean isPrototype(String name) throws Exception {
		return beanDefinitionMap.get(name).isPrototype();
	}
	
	@Override
	public boolean isSingleton(String name) throws Exception {
		return beanDefinitionMap.get(name).isSingleton();
	}
	
	/**
	 * 创建一个Bean实例
	 * @param beanDefinition
	 * @return
	 * @throws Exception
	 */
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception{
		Object bean = createBeanInstance(beanDefinition);
		//第一次setBean
		beanDefinition.setBean(bean);
		//实现属性的依赖注入的地方
		applyPropertyValues(bean,beanDefinition);
		return bean;
	}
	
	/**
	 * 预实例化非LazyInit选项的单例Bean
	 * lazy-init 设置只对scop属性为singleton的bean起作用
	 * @throws Exception
	 */
	public void preInitiateSingletons() throws Exception {
		for(Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
			String beanName = entry.getKey();
			BeanDefinition beanDefinition = entry.getValue();
			if(beanDefinition.isSingleton() && !beanDefinition.isLazyInit())
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
	@Override
	public void registerBeanDefinition(String name,BeanDefinition beanDefinition) {
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}
	
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.add(beanPostProcessor);
	}
	
	
}
