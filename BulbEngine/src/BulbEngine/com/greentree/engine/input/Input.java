package com.greentree.engine.input;

import static org.lwjgl.glfw.GLFW.*;
import com.greentree.engine.Game;

public class Input {
	
	private final static KeyListener keyListener = new KeyListener() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void keyPressed(int key) {
			Game.event(new KeyEvent(KeyEvent.EventType.keyPressed, key));
		}
		
		@Override
		public void keyReleased(int key) {
			Game.event(new KeyEvent(KeyEvent.EventType.keyReleased, key));
		}
	};
	private final static MouseListener mouseListener = new MouseListener() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void mouseDragged(int x1, int y1, int x2, int y2) {
			Game.event(new MouseEvent(MouseEvent.EventType.mouseDragged, x1, y1, x2, y2));
		}
		
		@Override
		public void mouseMoved(int x1, int y1, int x2, int y2) {
			Game.event(new MouseEvent(MouseEvent.EventType.mouseMoved, x1, y1, x2, y2));
		}
		
		@Override
		public void mousePressed(int button, int x, int y) {
			Game.event(new MouseEvent(MouseEvent.EventType.mousePressed, button, x, y));
		}
		
		@Override
		public void mouseReleased(int button, int x, int y) {
			Game.event(new MouseEvent(MouseEvent.EventType.mouseReleased, button, x, y));
		}
	};
	
	private Input() {
	}
	
	public static KeyListener getInputKeyListener() {
		return Input.keyListener;
	}
	
	public static MouseListener getInputMouseListener() {
		return Input.mouseListener;
	}
	
	/** @deprecated requires reassignment */
	@Deprecated
	public static Integer getKeyIndex(String substring) {
		return null;
	}
	
	public static String getKeyName(int key) {
		return glfwGetKeyName(key, glfwGetKeyScancode(key));
	}
	
	public static boolean isKeyDown(int key) {
		return Game.getWindow().isKeyDown(key);
	}
	
	public static int getIndexOf(String key) {
		return Game.getWindow().getIndexOf(key);
	}
}
