package com.greentree.engine.input;

import com.greentree.engine.event.Event;


/**
 * @author Arseny Latyshev
 *
 */
public class KeyRepaeasedEvent implements Event {

	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepaeasedEvent(final int key) {
		this.key = key;
	}
	
	public int getCode() {
		return key;
	}

	public KeyRepaeasedEvent reset(int key) {
		this.key = key;
		return this;
	}
	
}
