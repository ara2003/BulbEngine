package com.greentree.common.web;

import java.io.Serializable;

public class WebContext implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Object data;
	private final int name;
	private final int type;
	
	public WebContext(final int name, final byte type, final Object d) {
		this.name = name;
		data = d;
		this.type = type;
	}
	
	public WebContext(final int name, final Message message) {
		this.name = name;
		data = message.getData();
		type = message.getType();
	}
	
	public WebContext(final int name, final Object d) {
		this.name = name;
		data = d;
		type = 0;
	}
	
	public Object getData() {
		return data;
	}
	
	public int getName() {
		return name;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "(" + name + " " + type + " " + data + ")";
	}
}
