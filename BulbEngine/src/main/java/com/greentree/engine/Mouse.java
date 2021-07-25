package com.greentree.engine;

import com.greentree.common.logger.Log;
import com.greentree.engine.util.Windows;
import com.greentree.graphics.input.DoublePoisitionAction;

/**
 * @author Arseny Latyshev
 *
 */
public class Mouse {
	private static final DoublePoisitionAction mouseMoved = new DoublePoisitionAction(), mouseDragged = new DoublePoisitionAction();

	private final static boolean[] mouseButton = new boolean[3];
	private static int mouseX, mouseY;
	
	private static boolean ignore = true;

	public static boolean anyButtonPressed() {
		for(var b : mouseButton)if(b)return true;
		return false;
	}

	public static DoublePoisitionAction getMouseDragged() {
		return mouseDragged;
	}

	public static DoublePoisitionAction getMouseMoved() {
		return mouseMoved;
	}

	public static float getMouseX() {
		return mouseX;
	}

	public static float getMouseY() {
		return mouseY;
	}
	public static void init() {
		Windows.getWindow().getMousePosition().addListener((x, y) -> {
			if(ignore)ignore = false; else
				if(anyButtonPressed()) mouseDragged.action(mouseX, mouseY, x, y);
				else mouseMoved.action(mouseX, mouseY, x, y);

			mouseX = x;
			mouseY = y;
		});
		Windows.getWindow().getMouseButtonPress().addListener(b -> {
			try {
				mouseButton[b] = true;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
		});
		Windows.getWindow().getMouseButtonRelease().addListener(b -> {
			try {
				mouseButton[b] = false;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
		});
	}

	public static boolean isPressedMouseButton(int i) {
		return mouseButton[i];
	}

	public static void setMousePos(int x, int y) {
		ignore = true;
		Windows.getWindow().setMousePos(x, y);
	}

}
