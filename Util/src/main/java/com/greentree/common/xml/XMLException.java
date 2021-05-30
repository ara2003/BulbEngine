package com.greentree.common.xml;

public class XMLException extends Exception {

	private static final long serialVersionUID = 1L;

	public XMLException(final String message) {
		super(message);
	}

	public XMLException(final String message, final Throwable e) {
		super(message, e);
	}
}
