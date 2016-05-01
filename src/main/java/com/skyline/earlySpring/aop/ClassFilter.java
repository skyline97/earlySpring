package com.skyline.earlySpring.aop;

/**
 * 类过滤器
 */
public interface ClassFilter {

	boolean matches(Class<?> targetClass);
}
