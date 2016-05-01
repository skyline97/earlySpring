package com.skyline.earlySpring.aop;

/**
 * 通过ProxyFactory来得到具体的AOPProxy
 * @author skyline
 *
 */
public class ProxyFactory extends AdvisedSupport {

	/**
	 * 获得具体的AOPProxy,默认实现是JdkDynamicAopProxy
	 */
	public Object getProxy() {
		return createAopProxy();
	}
	
	private final AopProxy createAopProxy() {
		return new JdkDynamicAopProxy(this);
	}

	
}
