package com.greentree.engine;

import com.greentree.engine.core.util.Events;
import com.greentree.graphics.input.event.MouseClickEvent;
import com.greentree.graphics.input.event.MouseMovedEvent;

/**
 * @author Arseny Latyshev
 *
 */
public class Mouse {

	private final static boolean[] mouseButton = new boolean[2];
	private static int mouseX, mouseY;
	private static boolean ignore = true;
	
	static {
		Windows.window.getMousePosition().addListener((x, y) -> {
			if(ignore)ignore = false; else Events.event(MouseMovedEvent.getInstanse(Events.getEventsystem(), anyButtonPressed() ? MouseMovedEvent.EventType.mouseDragged : MouseMovedEvent.EventType.mouseMoved, mouseX, mouseY, x, y));
			mouseX = x;
			mouseY = y;
		});
		Windows.window.getMouseButtonPress().addListener(b -> {
			mouseButton[b] = true;
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mousePress, b, mouseX, mouseY));
		});
		Windows.window.getMouseButtonRelease().addListener(b -> {
			mouseButton[b] = false;
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mouseRelease, b, mouseX, mouseY));
		});
		Windows.window.getMouseButtonRepeat().addListener(b -> {
			Events.event(MouseClickEvent.getInstanse(Events.getEventsystem(), MouseClickEvent.EventType.mouseRepeat, b, mouseX, mouseY));
		});
	}

	public static boolean anyButtonPressed() {
		for(var b : mouseButton)if(b)return true;
		return false;
	}

	public static float getMouseX() {
		return mouseX;
	}

	public static float getMouseY() {
		return mouseY;
	}

	public static void setMousePos(int x, int y) {
		ignore = true;
		Windows.window.setMousePos(x, y);
	}

}
