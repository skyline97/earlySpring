package com.skyline.earlySpring.core;

import com.skyline.earlySpring.exception.BeanException;

public class BeanDefinition {

	private Object bean;
	
	private Class<?> beanClass;
	
	private String beanClassName;
	
	/**
	 * 
	 */
	private PropertyValues propertyValues = new PropertyValues();
	
	/**
	 * 默认是单例
	 */
	private boolean isSingleton = true;
	
	private boolean isPrototype = false;
	
	/**
	 * 懒加载默认为false
	 */
	private boolean lazyInit = false;
	
	public BeanDefinition() {
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			throw new BeanException("cant find bean named " + beanClassName);
		}
	}

	public boolean isSingleton() {
		return isSingleton;
	}

	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

	public boolean isPrototype() {
		return isPrototype;
	}

	public void setPrototype(boolean isPrototype) {
		this.isPrototype = isPrototype;
	}

	public boolean isLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	
}
