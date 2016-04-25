package com.skyline.tinySpring.core;

public interface BeanPostProcessor {

	Object postProcessBeforeInitialization(Object object,String beanName)
		throws Exception;
	
	Object postProcessAfterInitialization(Object object,String beanName)
		throws Exception;
	
	
}
