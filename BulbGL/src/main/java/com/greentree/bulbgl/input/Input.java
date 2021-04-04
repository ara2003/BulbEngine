package com.greentree.bulbgl.input;

import java.util.Objects;

import com.greentree.bulbgl.InputI;
import com.greentree.bulbgl.WindowI;
import com.greentree.bulbgl.input.listener.MouseListener;
import com.greentree.event.EventSystem;

public class Input {

	private static InputI input;
	private static WindowI window;
	private static int mouseX, mouseY;
	
	private Input() {
	}
	
	public static int getIndexOfKey(final String name) {
		return Input.input.getIndexOf(name);
	}
	
	public static String getKeyName(final int key) {
		return Input.input.getKeyName(key);
	}
	
	public static int getMouseX() {
		return Input.mouseX;
	}
	
	public static int getMouseY() {
		return Input.mouseY;
	}
	
	public static void setEventSystem(final EventSystem system) {
		Objects.requireNonNull(system).addListener(new MouseListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void mouseDragged(final int x1, final int y1, final int x2, final int y2) {
				Input.mouseX = x2 - window.getWidth()/2;
				Input.mouseY =  window.getHeight()/2 - y2;
			}
			
			@Override
			public void mouseMoved(final int x1, final int y1, final int x2, final int y2) {
				Input.mouseX = x2 - window.getWidth()/2;
				Input.mouseY = window.getHeight()/2 - y2;
			}
			
			@Override
			public void mousePress(final int button, final int x, final int y) {
			}
			
			@Override
			public void mouseRelease(final int button, final int x, final int y) {
			}
			
			@Override
			public void mouseRepeat(final int button, final int x, final int y) {
			}
		});
	}

	public static void setWindow(final WindowI window) {
		Input.window = Objects.requireNonNull(window);
	}
	public static void setBulbInput(final InputI input) {
		Input.input = Objects.requireNonNull(input);
	}
}
