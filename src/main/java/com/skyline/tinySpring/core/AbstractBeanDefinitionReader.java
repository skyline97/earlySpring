package com.skyline.tinySpring.core;

import java.util.HashMap;
import java.util.Map;

import com.skyline.tinySpring.io.ResourceLoader;


public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

	private Map<String, BeanDefinition> registry;
	
	private ResourceLoader resourceLoader;
	
	protected AbstractBeanDefinitionReader(ResourceLoader rl) {
		resourceLoader = rl;
		this.registry = new HashMap<String, BeanDefinition>();
	}
	
	protected Map<String, BeanDefinition> getRegistry() {
		return registry;
	}
	
	protected ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
