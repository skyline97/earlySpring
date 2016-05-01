package com.skyline.earlySpring.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * AOP Alliance MethodInvocation的具体实现
 * @author skyline
 *
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

	protected Object target;
	
	protected Method method;
	
	protected Object[] arguments;
	
	public ReflectiveMethodInvocation(Object target, Method method,
			Object[] arguments) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public Object proceed() throws Throwable {
		return method.invoke(target, arguments);
	}

	@Override
	public Object getThis() {
		return target;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	@Override
	public Method getMethod() {
		return method;
	}

}
