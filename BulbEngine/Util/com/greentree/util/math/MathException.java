package com.greentree.util.math;

/** @author ara */
public class MathException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
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
