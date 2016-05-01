package com.skyline.earlySpring;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import com.skyline.earlySpring.aop.AdvisedSupport;
import com.skyline.earlySpring.aop.AopProxy;
import com.skyline.earlySpring.aop.JdkDynamicAopProxy;
import com.skyline.earlySpring.aop.LogInterceptor;
import com.skyline.earlySpring.aop.MethodMatcher;
import com.skyline.earlySpring.aop.ProxyFactory;
import com.skyline.earlySpring.aop.TrueMatchMethodPointcut;
import com.skyline.earlySpring.aop.TargetSource;
import com.skyline.earlySpring.context.ApplicationContext;
import com.skyline.earlySpring.context.ClasspathXmlApplicationContext;
import com.skyline.earlySpring.core.BeanDefinitionReader;
import com.skyline.earlySpring.core.BeanPostProcessor;
import com.skyline.earlySpring.factory.AutowiredBeanFactory;
import com.skyline.earlySpring.factory.BeanFactory;
import com.skyline.earlySpring.io.ClassPathResource;
import com.skyline.earlySpring.io.Resource;
import com.skyline.earlySpring.xml.XmlBeanDefinitionReader;

public class TestEarlySpring {
	

	@Test
	public void testIOC() throws Exception {
		BeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = new ClassPathResource("beans.xml");
		reader.loadBeanDefinitions(resource);
		
		User u = beanFactory.getBean("user",User.class);
		System.out.println(u);
	}
	
	@Test
	public void testIOC02() throws Exception {
		BeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:beans.xml");
		
		User u = beanFactory.getBean(User.class);
		User u2 = beanFactory.getBean("user2",User.class);
		System.out.println(u);
		System.out.println(u2);
	}
	
	@Test
	public void testRefAndScope() throws Exception {
		BeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:beans.xml");
		
		//有一个属性是ref类型
		User u = beanFactory.getBean("user3",User.class);
		User u2 = (User) beanFactory.getBean("user3");
		System.out.println(u);
		//user3是多例,所以输出为false
		System.out.println(u == u2);
	}
	
	@Test
	public void testBeanPostProcessor() throws Exception {
		AutowiredBeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:beans.xml");
		
		beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
			
			@Override
			public Object postProcessBeforeInitialization(Object object, String beanName)
					throws Exception {
				System.out.println("before ----" + beanName);
				return object;
			}
			
			@Override
			public Object postProcessAfterInitialization(Object object, String beanName)
					throws Exception {
				System.out.println("after -----" + beanName);
				return object;
			}
		});
		
		Address a = beanFactory.getBean("address",Address.class);
		System.out.println(a);
		
	}
	
	@Test
	public void testApplicationContext() throws Exception {
		ApplicationContext context = new ClasspathXmlApplicationContext("beans.xml");
		Address a = context.getBean("address",Address.class);
		System.out.println(a);
	}
	
	@Test
	public void testApplicationContext2() throws Exception {
		ApplicationContext context = new ClasspathXmlApplicationContext("beans.xml");
		User u = context.getBean("user3",User.class);
		User u2 = context.getBean("user3",User.class);
		System.out.println(u);
		System.out.println(u2);
		System.out.println(u == u2);
	}
	
	@Test
	public void testBeanPostProcessor2() throws Exception {
		ApplicationContext context = new ClasspathXmlApplicationContext("beans.xml");
		Address a = context.getBean("address",Address.class);
		System.out.println(a);
	}
	
	@Test
	public void testAop01() throws Exception {
		ApplicationContext context = new ClasspathXmlApplicationContext("beans.xml");
		Address address = context.getBean("address",Address.class);
		
		//设置被代理对象
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(Address.class,Address.class.getInterfaces() ,address);
		advisedSupport.setTargetSource(targetSource);
		
		//设置拦截器
		MethodInterceptor methodInterceptor = new LogInterceptor();
		advisedSupport.setMethodInterceptor(methodInterceptor);
		
		//设置方法匹配器
		MethodMatcher methodMatcher = new TrueMatchMethodPointcut();
		advisedSupport.setMethodMatcher(methodMatcher);
		
		JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
		AInterface proxy = (AInterface) jdkDynamicAopProxy.getProxy();
		
		proxy.log();
	}
	
	@Test
	public void testProxyFactory() throws Exception {
		ApplicationContext context = new ClasspathXmlApplicationContext("beans.xml");
		Address address = context.getBean("address",Address.class);
		
		ProxyFactory proxyFactory = new ProxyFactory();
		
		TargetSource targetSource = new TargetSource(Address.class,Address.class.getInterfaces() ,address);
		proxyFactory.setTargetSource(targetSource);
		
		MethodInterceptor methodInterceptor = new LogInterceptor();
		proxyFactory.setMethodInterceptor(methodInterceptor);
		
		MethodMatcher methodMatcher = new TrueMatchMethodPointcut();
		proxyFactory.setMethodMatcher(methodMatcher);
		
		AInterface proxy = (AInterface) ((AopProxy)proxyFactory.getProxy()).getProxy();
		proxy.log();
	}

}
