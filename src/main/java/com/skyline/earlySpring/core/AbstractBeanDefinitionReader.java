package com.skyline.earlySpring.core;

import com.skyline.earlySpring.io.DefaultResourceLoader;
import com.skyline.earlySpring.io.ResourceLoader;


public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

	/**
	 * 实际上registry就是AutowiredBeanFactory,因为BeanFactory实现了BeanRegister接口
	 */
	private final BeanDefinitionRegister registry;
	
	/**
	 * DefaultResoueceLoader是唯一实现-_-
	 */
	private final ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	protected AbstractBeanDefinitionReader(BeanDefinitionRegister registry) {
		this.registry = registry;
	}
	
	protected BeanDefinitionRegister getRegistry() {
		return registry;
	}
	
	protected ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
