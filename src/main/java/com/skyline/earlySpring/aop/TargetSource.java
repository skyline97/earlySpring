package com.skyline.earlySpring.aop;

/**
 * 被代理的对象元数据
 * @author skyline
 *
 */
public class TargetSource {

	/**
	 *被代理的对象的类
	 */
	private Class<?> targetClass;
	
	/**
	 * 被代理的对象所实现的接口
	 */
	private Class<?>[] interfaces;
	
	/**
	 * 被代理的对象
	 */
	private Object target;

	public TargetSource(Class<?> targetClass, Class<?>[] interfaces,
			Object target) {
		super();
		this.targetClass = targetClass;
		this.interfaces = interfaces;
		this.target = target;
	}
	
	public Object getTarget() {
		return target;
	}
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Class<?>[] getInterfaces() {
		return interfaces;
	}
	
	public void setInterfaces(Class<?>[] interfaces) {
		this.interfaces = interfaces;
	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
}
