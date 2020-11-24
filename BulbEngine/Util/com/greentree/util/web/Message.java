package com.greentree.util.web;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Object data;
	private final int type;
	
	public Message(final int type, final Object object) {
		data = object;
		this.type = type;
	}

	public Object getData() {
		return data;
	}
	
	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "(" + type + " " + data + ")";
	}
}
