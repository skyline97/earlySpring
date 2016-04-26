package com.skyline.earlySpring;

import org.junit.Test;

import com.skyline.earlySpring.core.BeanDefinitionReader;
import com.skyline.earlySpring.factory.AutowiredBeanFactory;
import com.skyline.earlySpring.factory.BeanFactory;
import com.skyline.earlySpring.io.ClassPathResource;
import com.skyline.earlySpring.io.Resource;
import com.skyline.earlySpring.xml.XmlBeanDefinitionReader;

public class TestEarlySpring {

	@Test
	public void test01() throws Exception {
		BeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = new ClassPathResource("beans.xml");
		reader.loadBeanDefinitions(resource);
		
		User u = beanFactory.getBean("user",User.class);
		System.out.println(u);
	}
	
	@Test
	public void test02() throws Exception {
		BeanFactory beanFactory = new AutowiredBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:beans.xml");
		
		User u = beanFactory.getBean(User.class);
		User u2 = beanFactory.getBean("user2",User.class);
		System.out.println(u);
		System.out.println(u2);
	}

}
