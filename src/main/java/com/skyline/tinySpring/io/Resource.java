package com.skyline.tinySpring.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 封装了IO操作
 * @author skyline
 *
 */
public interface Resource {

	InputStream getInputStream() throws IOException;
}
