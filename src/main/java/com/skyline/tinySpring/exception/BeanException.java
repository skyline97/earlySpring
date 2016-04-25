package com.skyline.tinySpring.exception;

public class BeanException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeanException() {
		super();
	}

	public BeanException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeanException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanException(String message) {
		super(message);
	}

	public BeanException(Throwable cause) {
		super(cause);
	}
	
	

}
