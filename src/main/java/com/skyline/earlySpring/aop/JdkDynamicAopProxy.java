package com.skyline.earlySpring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 基于JDK动态代理的AOP代理
 * @author skyline
 *
 */
public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {
	
	public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
		super(advisedSupport);
	}

	/**
	 * 获取代理对象
	 */
	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(getClass().getClassLoader(), 
				advisedSupport.getTargetSource().getInterfaces(), this);
	}
	
	/**
	 * 实现了InvacationHandler接口,重写invoke方法,并将自身对象传递给Proxy的newProxyInstance
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
		MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();
		if(methodMatcher != null && methodMatcher.matches(method, 
				advisedSupport.getTargetSource().getTarget().getClass())) {
			return methodInterceptor.invoke(new ReflectiveMethodInvocation(
					advisedSupport.getTargetSource().getTarget()
					, method, args));
		}
		else {
			return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
		}
	}
}
