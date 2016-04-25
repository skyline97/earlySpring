package com.skyline.tinySpring.io;

import java.net.MalformedURLException;
import java.net.URL;

public class ResourceLoader {
	
	public static final String CLASSPATH_PREFIX = "classpath:";

	public Resource getResource(String location) {
		if(location == null)
			throw new IllegalArgumentException(location + " not exists");
		if(location.startsWith(CLASSPATH_PREFIX))
			return new ClassPathResource(location);
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
