package com.greentree.util.web;

import java.io.Serializable;

public class WebContext implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Object data;
	private final int name;
	private long time;
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

	public int getDelta() {
		return (int) (System.currentTimeMillis() - time);
	}

	public int getName() {
		return name;
	}

	public int getType() {
		return type;
	}
	
	public void send() {
		time = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "(" + name + " " + type + " " + data + ")";
	}
}
