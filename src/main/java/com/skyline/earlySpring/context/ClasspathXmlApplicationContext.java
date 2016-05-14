package com.skyline.earlySpring.context;

import com.skyline.earlySpring.io.ClassPathResource;
import com.skyline.earlySpring.io.Resource;
import com.skyline.earlySpring.xml.XmlBeanDefinitionReader;

/**
 * 文件地址位于ClassPath且资源文件形式为XML的上下文环境,
 * @author skyline
 *
 */
public class ClasspathXmlApplicationContext extends AbstractApplicationContext{
	
	private Resource resource;

	/**
	 * 调用该构造方法容器自动初始化完成并完成单例Bean的预实例化和BeanPostProcessor的注册
	 * @param location
	 * @throws Exception
	 */
	public ClasspathXmlApplicationContext(String location) throws Exception {
		this.resource = new ClassPathResource(location);
		
		refresh();
	}

	@Override
	public Resource getResource(String location) {
		return resource;
	}

	@Override
	public void loadBeanDefinitions() throws Exception {
		
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);
		
		reader.loadBeanDefinitions(resource);
	}

}
