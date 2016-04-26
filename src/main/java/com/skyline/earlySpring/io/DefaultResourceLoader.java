package com.skyline.earlySpring.io;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {
	

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
