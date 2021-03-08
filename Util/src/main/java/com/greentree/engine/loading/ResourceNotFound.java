package com.greentree.engine.loading;


/**
 * @author Arseny Latyshev
 *
 */
public class ResourceNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFound() {
		super();
	}

	public ResourceNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFound(String message) {
		super(message);
	}

	public ResourceNotFound(Throwable cause) {
		super(cause);
	}
	
}
