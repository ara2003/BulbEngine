package com.greentree.engine;

import com.greentree.common.logger.Log;
import com.greentree.engine.core.util.Events;
import com.greentree.graphics.input.DoublePoisitionAction;
import com.greentree.graphics.input.event.MouseClickEvent;
import com.greentree.graphics.input.event.MouseMovedEvent;

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
		Windows.window.getMousePosition().addListener((x, y) -> {
			if(ignore)ignore = false; else
				if(anyButtonPressed()) {
					Events.event(MouseMovedEvent.getInstanse(Events.getEventsystem(), MouseMovedEvent.EventType.mouseDragged , mouseX, mouseY, x, y));
					mouseDragged.action(mouseX, mouseY, x, y);
				}else {
					Events.event(MouseMovedEvent.getInstanse(Events.getEventsystem(), MouseMovedEvent.EventType.mouseMoved, mouseX, mouseY, x, y));
					mouseMoved.action(mouseX, mouseY, x, y);
				}

			mouseX = x;
			mouseY = y;
		});
		Windows.window.getMouseButtonPress().addListener(b -> {
			try {
				mouseButton[b] = true;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mousePress, b, mouseX, mouseY));
		});
		Windows.window.getMouseButtonRelease().addListener(b -> {
			try {
				mouseButton[b] = false;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mouseRelease, b, mouseX, mouseY));
		});
		Windows.window.getMouseButtonRepeat().addListener(b -> {
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mouseRepeat, b, mouseX, mouseY));
		});
	}

	public static void setMousePos(int x, int y) {
		ignore = true;
		Windows.window.setMousePos(x, y);
	}

}
