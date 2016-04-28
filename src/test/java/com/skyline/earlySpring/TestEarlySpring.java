package com.skyline.earlySpring;

import org.junit.Test;

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

}
