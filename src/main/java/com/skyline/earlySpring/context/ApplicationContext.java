package com.skyline.earlySpring.context;

import com.skyline.earlySpring.factory.BeanFactory;
import com.skyline.earlySpring.io.ResourceLoader;

/**
 * 注意ApplicationContext实现了ResourceLoader接口,能够依靠自己完成资源的载入
 * @author skyline
 *
 */
public interface ApplicationContext extends BeanFactory,ResourceLoader{

}
