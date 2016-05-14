package com.skyline.earlySpring.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认的ResourceLoader,其中的getResource方法可以根据字符串读取Resource
 * @author skyline
 *
 */
public class DefaultResourceLoader implements ResourceLoader {
	
	
	/**
	 * 根据字符串的形式来判断具体是哪种资源
	 */
	@Override
	public Resource getResource(String location) {
		if(location == null || location.equals(""))
			throw new IllegalArgumentException(location + " not exists");
		if(location.startsWith(CLASSPATH_PREFIX))
			return new ClassPathResource(location.substring(CLASSPATH_PREFIX.length()));
		else {
			try {
				URL url = new URL(location);
				return new URLResource(url);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
