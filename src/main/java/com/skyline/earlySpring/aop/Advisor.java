package com.skyline.earlySpring.aop;

import org.aopalliance.aop.Advice;

/**
 * 通知器
 * @author skyline
 *
 */
public interface Advisor {

	Advice getAdvice();
}
