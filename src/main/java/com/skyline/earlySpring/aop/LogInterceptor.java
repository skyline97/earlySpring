package com.skyline.earlySpring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("interceptor");
		Object proceed = invocation.proceed();
		return proceed;
	}
	
}
