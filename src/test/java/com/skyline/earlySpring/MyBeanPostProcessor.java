package com.skyline.earlySpring;

import com.skyline.earlySpring.core.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object object, String beanName)
			throws Exception {
		System.out.println("postProcessAfterInitialization");
		return object;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object object, String beanName)
			throws Exception {
		System.out.println("postProcessBeforeInitialization");
		return object;
	}
}
