package com.skyline.earlySpring.aop;

/**
 * AOP代理
 * @author skyline
 *
 */
public interface AopProxy {

	/**
	 * 生成具体代理对象
	 * @return
	 */
	Object getProxy();
}
