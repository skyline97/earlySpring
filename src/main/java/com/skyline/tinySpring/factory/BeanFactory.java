package com.skyline.tinySpring.factory;


public interface BeanFactory {

	Object getBean(String name) throws Exception;
	
	<T> T getBean(String name,Class<T> clazz) throws Exception;
	
	<T> T getBean(Class<T> clazz) throws Exception;
	
	boolean containsBean(String name) throws Exception;
	
	/**
	 * 判断名称为name的Bean是否是单例模式
	 * @param name
	 * @return
	 * @throws Exception
	 */
	boolean isSingleton(String name) throws Exception;
	
	/**
	 * 判断名称为name的Bean是否是多例
	 * @param name
	 * @return
	 * @throws Exception
	 */
	boolean isPrototype(String name) throws Exception;
	
}
