package com.greentree.data.loading;


/** @author Arseny Latyshev */
public class ResourceNotFound extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFound() {
	}
	
	public ResourceNotFound(final String message) {
		super(message);
	}
	
	public ResourceNotFound(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public ResourceNotFound(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public ResourceNotFound(final Throwable cause) {
		super(cause);
	}
	
}
