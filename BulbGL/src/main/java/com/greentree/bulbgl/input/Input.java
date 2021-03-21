package com.greentree.bulbgl.input;

import java.util.Objects;

import org.joml.Vector2i;
import org.joml.Vector2ic;

import com.greentree.bulbgl.Window;
import com.greentree.bulbgl.input.listener.MouseListener;
import com.greentree.engine.event.EventSystem;

public class Input {
	
	private static Window window;
	private static int mouseX, mouseY;
	
	private Input() {
	}
	
	public static int getIndexOfKey(final String name) {
		return Input.window.getIndexOf(name);
	}
	
	public static String getKeyName(final int key) {
		return Input.window.getKeyName(key);
	}
	
	@SuppressWarnings("exports")
	public static Vector2ic getMouse() {
		return new Vector2i(Input.mouseX, Input.mouseY);
	}
	
	@SuppressWarnings("exports")
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
	
	public static void setWindow(final Window window) {
		Input.window = Objects.requireNonNull(window);
	}
}
