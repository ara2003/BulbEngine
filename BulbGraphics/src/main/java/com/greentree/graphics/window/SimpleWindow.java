package com.greentree.graphics.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import com.greentree.action.EventAction;
import com.greentree.graphics.GLFWContext;
import com.greentree.graphics.Window;
import com.greentree.graphics.input.PoisitionAction;


/** @author Arseny Latyshev */
public class SimpleWindow extends Window {

	private final EventAction<Integer> keyPress, keyRelease, keyRepeat, keyPressOrRepeat;
	private final EventAction<Integer> mouseButtonPress, mouseButtonRelease, mouseButtonRepeat;
	private final EventAction<String> charEnter;

	public EventAction<String> getCharEnter() {
		return charEnter;
	}

	private final PoisitionAction mousePosition;
	{
		keyPress   = new EventAction<>();
		keyRelease = new EventAction<>();
		keyRepeat  = new EventAction<>();
		charEnter  = new EventAction<>();
		keyPressOrRepeat  = new EventAction<>();
		this.setCallback((GLFWKeyCallbackI) (window, key, scancode, action, mods)-> {
			if(window != getId()) return;
			if(action == GLFW.GLFW_RELEASE) keyRelease.action(key);
			if(action == GLFW.GLFW_REPEAT) {
				keyPressOrRepeat.action(key);
				keyRepeat.action(key);
			}
			if(action == GLFW.GLFW_PRESS) {
				keyPressOrRepeat.action(key);
				keyPress.action(key);
			}
		});
		this.setCallback((GLFWCharCallbackI) (window, codepoint)-> {
			charEnter.action(Character.toString(codepoint));
		});
		mouseButtonPress   = new EventAction<>();
		mouseButtonRelease = new EventAction<>();
		mouseButtonRepeat  = new EventAction<>();
		this.setCallback((GLFWMouseButtonCallbackI) (window, button, action, mods)-> {
			if(action == GLFW.GLFW_PRESS) SimpleWindow.this.mouseButtonPress.action(button);
			if(action == GLFW.GLFW_RELEASE) SimpleWindow.this.mouseButtonRelease.action(button);
			if(action == GLFW.GLFW_REPEAT) SimpleWindow.this.mouseButtonRepeat.action(button);
		});
		mousePosition = new PoisitionAction();
		this.setCallback((GLFWCursorPosCallbackI) (window, xpos, ypos)-> {
			mousePosition.action((int)(xpos-getWidth()/2f), (int)(getHeight()/2f-ypos));
		});

	}

	public void setWindowCloseCallback(final Runnable listener) {
		setCallback((GLFWWindowCloseCallbackI)w -> listener.run());
	}
	
	public SimpleWindow(final String title, final int width, final int height) {
		this(title, width, height, null);
	}

	public SimpleWindow(final String title, final int width, final int height, final boolean resizable, final boolean fullscreen) {
		super(title, width, height, resizable, fullscreen, null);
	}

	public SimpleWindow(final String title, final int width, final int height, GLFWContext share) {
		super(title, width, height, true, false, share);
	}

	public EventAction<Integer> getKeyPress() {
		return keyPress;
	}

	public EventAction<Integer> getKeyRelease() {
		return keyRelease;
	}

	public EventAction<Integer> getKeyRepeat() {
		return keyRepeat;
	}

	public EventAction<Integer> getMouseButtonPress() {
		return mouseButtonPress;
	}
	public EventAction<Integer> getMouseButtonRelease() {
		return mouseButtonRelease;
	}

	public EventAction<Integer> getMouseButtonRepeat() {
		return mouseButtonRepeat;
	}

	public PoisitionAction getMousePosition() {
		return mousePosition;
	}

	public void setMousePos(int x, int y) {
		GLFW.glfwSetCursorPos(getId(), x + getWidth() / 2, y + getHeight() / 2);
	}

	public EventAction<Integer> getKeyPressOrRepeat() {
		return keyPressOrRepeat;
	}

}
