package com.greentree.engine.opengl;

/** @author Arseny Latyshev */
public class ImageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ImageException(String message, String ref) {
		super(message + " : " + ref);
	}
	
	public ImageException(String message) {
		super(message);
	}

	public ImageException(String message, String ref, Throwable cause) {
		super(message + " : " + ref, cause);
	}
	
	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ImageException(Throwable cause) {
		super(cause);
	}
}
