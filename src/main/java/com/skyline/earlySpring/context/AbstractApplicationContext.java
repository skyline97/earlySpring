package com.skyline.earlySpring.context;

import com.skyline.earlySpring.core.BeanDefinition;
import com.skyline.earlySpring.core.BeanPostProcessor;
import com.skyline.earlySpring.factory.AbstractBeanFactory;
import com.skyline.earlySpring.factory.AutowiredBeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext {

	protected AutowiredBeanFactory autowiredBeanFactory;
	
	protected AbstractApplicationContext() {
		autowiredBeanFactory = createBeanFactory();
	}
	
	/**
	 * 容器初始化的入口
	 */
	protected void refresh() throws Exception {
		loadBeanDefinitions();
		
		//注册BeanPostProcessors
		registerBeanPostProcessors(autowiredBeanFactory);
		
		//预实例化单例Bean
		this.autowiredBeanFactory.preInitiateSingletons();
	}
	
	/**
	 * 将XML配置文件中的BeanPostProcessor注册到容器
	 * @param beanFactory
	 * @throws Exception
	 */
	private void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
		BeanPostProcessor beanPostProcessor = beanFactory.getBean(BeanPostProcessor.class);
		beanFactory.addBeanPostProcessor(beanPostProcessor);
	}
	
	/**
	 * 模板方法,由具体子类实现
	 * @param beanFactory
	 * @throws Exception 
	 */
	protected abstract void loadBeanDefinitions() throws Exception; 
	
	
	private AutowiredBeanFactory createBeanFactory() {
		return new AutowiredBeanFactory();
	}
	
	@Override
	public void registerBeanDefinition(String name,
			BeanDefinition beanDefinition) {
		this.autowiredBeanFactory.registerBeanDefinition(name, beanDefinition);
	}
	
	@Override
	public boolean isPrototype(String name) throws Exception {
		return this.autowiredBeanFactory.isPrototype(name);
	}
	
	@Override
	public boolean isSingleton(String name) throws Exception {
		return this.autowiredBeanFactory.isSingleton(name);
	}
	
	@Override
	public boolean containsBean(String name) throws Exception {
		return this.autowiredBeanFactory.containsBean(name);
	}
	
	@Override
	public Object getBean(String name) throws Exception {
		return this.autowiredBeanFactory.getBean(name);
	}
	
	@Override
	public <T> T getBean(Class<T> clazz) throws Exception {
		return this.autowiredBeanFactory.getBean(clazz);
	}
	
	@Override
	public <T> T getBean(String name, Class<T> clazz) throws Exception {
		return this.autowiredBeanFactory.getBean(name, clazz);
	}
}
