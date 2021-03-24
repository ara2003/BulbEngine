package com.greentree.common.math;

/** @author Arseny Latyshev */
public class MathException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public MathException() {
	}
	
	/** @param message */
	public MathException(final String message) {
		super(message);
	}
	
	/** @param message
	 * @param cause */
	public MathException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace */
	public MathException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/** @param cause */
	public MathException(final Throwable cause) {
		super(cause);
	}
}
