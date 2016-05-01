package com.skyline.earlySpring.aop;

/**
 * MethodMatcher对象实际上可以被配置成NameMatcherMethodPointcut来完成方法匹配判断
 * @author skyline
 *
 */
public abstract class MethodMatcherPointcut implements Pointcut,MethodMatcher{

	protected MethodMatcher methodMatcher;
	
	protected ClassFilter classFilter;
	
	@Override
	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}
	
	@Override
	public ClassFilter getClassFilter() {
		return classFilter;
	}
}
