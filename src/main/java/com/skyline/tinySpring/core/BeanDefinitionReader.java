package com.skyline.tinySpring.core;


public interface BeanDefinitionReader {

	void loadBeanDefinitions(String location) throws Exception;
}
