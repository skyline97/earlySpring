package com.skyline.earlySpring.aop;

public interface PointcutAdvisor extends Advisor {

	Pointcut getPointcut();
}
