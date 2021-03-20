package com.greentree.bulbgl.input;

import com.greentree.bulbgl.Window;

public class Input {
	
	private static Window window;
	
	private Input() {
	}
	
	public static void init(Window window) {
		Input.window = window;
	}
	
	public static String getKeyName(int key) {
		return window.getKeyName(key);
	}

	public static int getIndexOfKey(String name) {
		return window.getIndexOf(name);
	}
	
}
