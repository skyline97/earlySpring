package com.skyline.earlySpring.io;

public interface ResourceLoader {

	
	String CLASSPATH_PREFIX = "classpath:";
	
	/**
	 * 根据Location的内容获取具体的Resource
	 * @param location
	 * @return
	 */
	Resource getResource(String location);
}
