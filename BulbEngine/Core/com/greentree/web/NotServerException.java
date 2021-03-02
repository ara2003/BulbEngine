package com.greentree.web;

public class NotServerException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NotServerException(final String string) {
		super(string);
	}
	
	public NotServerException(final Throwable e) {
		super(e);
	}
}
