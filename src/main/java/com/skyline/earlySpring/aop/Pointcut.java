package com.skyline.earlySpring.aop;

/**
 * 切点
 * @author skyline
 *
 */
public interface Pointcut {

	ClassFilter getClassFilter();
	
	MethodMatcher getMethodMatcher();
}
