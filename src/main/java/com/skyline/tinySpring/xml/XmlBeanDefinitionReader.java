package com.skyline.tinySpring.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.skyline.tinySpring.core.AbstractBeanDefinitionReader;
import com.skyline.tinySpring.core.BeanDefinition;
import com.skyline.tinySpring.core.BeanReference;
import com.skyline.tinySpring.core.PropertyValue;
import com.skyline.tinySpring.io.ResourceLoader;

/**
 * 对xml进行解析的类
 * @author skyline
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

	protected XmlBeanDefinitionReader(ResourceLoader rl) {
		super(rl);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream in = getResourceLoader().getResource(location).getInputStream();
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
	
	private void parseBeanDefinitions(Element root) {
		NodeList nodeList = root.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}
	
	private void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		
		//beanDefinition的singleton属性默认是true
		String scope = ele.getAttribute("scope");
		BeanDefinition beanDefinition = new BeanDefinition();
		if(scope != null && scope.equals("singleton"))
			beanDefinition.setSingleton(true);
		else if(scope != null && scope.equals("prototype"))
			beanDefinition.setSingleton(false);
		else
			throw new RuntimeException("illegal scope");
		
		processProperty(ele, beanDefinition);
		beanDefinition.setBeanClassName(className);
	
		
		//
		getRegistry().put(name, beanDefinition);
	}
	
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
