package com.skyline.earlySpring.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.skyline.earlySpring.core.AbstractBeanDefinitionReader;
import com.skyline.earlySpring.core.BeanDefinition;
import com.skyline.earlySpring.core.BeanDefinitionRegister;
import com.skyline.earlySpring.core.BeanReference;
import com.skyline.earlySpring.core.PropertyValue;
import com.skyline.earlySpring.io.Resource;

/**
 * 对xml进行解析的类
 * @author skyline
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

	public XmlBeanDefinitionReader(BeanDefinitionRegister register) {
		super(register);
	}
	
	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream in = getResourceLoader().getResource(location).getInputStream();
		doLoadBeanDefinitions(in);
	}

	
	@Override
	public void loadBeanDefinitions(Resource resource) throws Exception {
		InputStream in = resource.getInputStream();
		doLoadBeanDefinitions(in);
	}

	private void doLoadBeanDefinitions(InputStream in) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(in);
		
		//解析Bean
		registerBeanDefinitions(doc);
		
		in.close();
	}
	
	
	private void registerBeanDefinitions(Document doc) {
		Element root = doc.getDocumentElement();
		parseBeanDefinitions(root);
	}
	
	/**
	 * 解析出xml中的<bean>元素
	 * @param root
	 */
	private void parseBeanDefinitions(Element root) {
		NodeList nodeList = root.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node instanceof Element) {
				//此时的node就是<bean>
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}
	
	/**
	 * 解析<bean>元素中的信息,通过一个beanDefinition进行封装
	 * @param ele
	 */
	private void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		
		//beanDefinition的singleton属性默认是true
		String scope = ele.getAttribute("scope");
		BeanDefinition beanDefinition = new BeanDefinition();
		if(scope != null && !scope.equals("")) {
			if(scope.equals("singleton"))
				beanDefinition.setSingleton(true);
			else if(scope.equals("prototype")) {
				beanDefinition.setSingleton(false);
				beanDefinition.setPrototype(true);
			}
			else
				throw new RuntimeException("illegal scope");
		}
		
		//解析lazyInit属性
		String lazyInit = ele.getAttribute("lazyInit");
		if(lazyInit != null && lazyInit.equals("true"))
			beanDefinition.setLazyInit(true);
		
		processProperty(ele, beanDefinition);
		
		//设置BeanClassName同时会设置BeanClass,此时不会setBean
		beanDefinition.setBeanClassName(className);
	
		//向容器中注册
		getRegistry().registerBeanDefinition(name, beanDefinition);
	}
	
	/**
	 * 解析<bean>中的<property>,然后设置到beanDefinition中
	 * @param ele
	 * @param beanDefinition
	 */
	private void processProperty(Element ele,BeanDefinition beanDefinition) {
		NodeList propertyNode = ele.getElementsByTagName("property");
		for(int i = 0;i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if(node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				if(value != null && value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				} 
				else {
					//属性为ref的情况
					String ref = propertyEle.getAttribute("ref");
					if(ref == null || ref.length() == 0) {
						throw new IllegalArgumentException("ref can not be null");
					}
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
	
	

	
}
