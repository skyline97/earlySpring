package com.skyline.earlySpring.core;

import com.skyline.earlySpring.io.Resource;



public interface BeanDefinitionReader {
	
	void loadBeanDefinitions(String location) throws Exception;

	void loadBeanDefinitions(Resource resource) throws Exception;
}
