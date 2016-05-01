package com.skyline.earlySpring.aop;

public abstract class AbstractAopProxy implements AopProxy {

	protected AdvisedSupport advisedSupport;
	
	protected AbstractAopProxy(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}
}
