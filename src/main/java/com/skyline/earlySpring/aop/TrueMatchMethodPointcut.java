package com.skyline.earlySpring.aop;

import java.lang.reflect.Method;

public class TrueMatchMethodPointcut extends MethodMatcherPointcut {

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return true;
	}
	
}
