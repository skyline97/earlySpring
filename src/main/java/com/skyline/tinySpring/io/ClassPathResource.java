package com.skyline.tinySpring.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

	private String classpath;
	
	public ClassPathResource(String classpath) {
		this.classpath = classpath;
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		InputStream in = getClass().getResourceAsStream(classpath);
		if (in == null) {
			throw new FileNotFoundException("it does not exist");
		}
		return in;
	}
}
