package com.greentree.engine.corutine;

import com.greentree.engine.input.Input;

public class WaitKeyPresed implements CustomResemInstruction {
	
	private final int key;
	
	public WaitKeyPresed(final int key) {
		this.key = key;
	}
	
	@Override
	public boolean keepWaiting() {
		return Input.isKeyDown(key);
	}
}
