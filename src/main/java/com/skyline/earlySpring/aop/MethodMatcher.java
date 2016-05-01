package com.skyline.earlySpring.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * @author skyline
 *
 */
public interface MethodMatcher {

	boolean matches(Method method, Class<?> targetClass);
}
