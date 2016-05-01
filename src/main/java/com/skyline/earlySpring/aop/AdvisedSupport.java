package com.skyline.earlySpring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 
 * @author skyline
 *
 */
public class AdvisedSupport {

	/**
	 * 目标对象的元数据
	 */
	private TargetSource targetSource;
	
	/**
	 * 方法拦截器
	 */
	private MethodInterceptor methodInterceptor;
	
	/**
	 * 方法匹配器
	 */
	private MethodMatcher methodMatcher;

	public TargetSource getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(TargetSource targetSource) {
		this.targetSource = targetSource;
	}

	public MethodInterceptor getMethodInterceptor() {
		return methodInterceptor;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

	public void setMethodMatcher(MethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}
	
	
}
