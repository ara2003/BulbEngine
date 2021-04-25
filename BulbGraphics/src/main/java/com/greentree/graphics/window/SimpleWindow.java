package com.greentree.graphics.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import com.greentree.action.EventAction;
import com.greentree.graphics.Window;
import com.greentree.graphics.input.PoisitionAction;


/** @author Arseny Latyshev */
public class SimpleWindow extends Window {
	
	private final EventAction<Integer> keyPress, keyRelease, keyRepeat;
	private final EventAction<Integer> mouseButtonPress, mouseButtonRelease, mouseButtonRepeat;
	private final PoisitionAction mousePosition;
	{
		this.keyPress   = new EventAction<>();
		this.keyRelease = new EventAction<>();
		this.keyRepeat  = new EventAction<>();
		this.setCallback((GLFWKeyCallbackI) (window, key, scancode, action, mods)-> {
			if(window != this.getId()) return;
			if(action == GLFW.GLFW_RELEASE) this.keyRelease.action(key);
			if(action == GLFW.GLFW_REPEAT) this.keyRepeat.action(key);
			if(action == GLFW.GLFW_PRESS) this.keyPress.action(key);
		});
		this.mouseButtonPress   = new EventAction<>();
		this.mouseButtonRelease = new EventAction<>();
		this.mouseButtonRepeat  = new EventAction<>();
		this.setCallback((GLFWMouseButtonCallbackI) (window, button, action, mods)-> {
			if(window != SimpleWindow.this.getId()) return;
			if(action == GLFW.GLFW_RELEASE) SimpleWindow.this.mouseButtonPress.action(button);
			if(action == GLFW.GLFW_REPEAT) SimpleWindow.this.mouseButtonRelease.action(button);
			if(action == GLFW.GLFW_PRESS) SimpleWindow.this.mouseButtonRepeat.action(button);
		});
		this.mousePosition = new PoisitionAction();
		this.setCallback((GLFWCursorPosCallbackI) (window, xpos, ypos)-> {
			this.mousePosition.action((int)(xpos-getWidth()/2f), (int)(getHeight()/2f-ypos));
		});
	}
	
	public void setMousePos(int x, int y) {
		GLFW.glfwSetCursorPos(getId(), x + getWidth() / 2, y + getHeight() / 2);
	}
	
	public SimpleWindow(final String title, final int width, final int height) {
		super(title, width, height, true, false);
	}
	
	public SimpleWindow(final String title, final int width, final int height, final boolean resizable, final boolean fullscreen) {
		super(title, width, height, resizable, fullscreen);
	}
	
	public EventAction<Integer> getKeyPress() {
		return this.keyPress;
	}
	
	public EventAction<Integer> getKeyRelease() {
		return this.keyRelease;
	}
	
	public EventAction<Integer> getKeyRepeat() {
		return this.keyRepeat;
	}
	
	public EventAction<Integer> getMouseButtonPress() {
		return this.mouseButtonPress;
	}
	
	public EventAction<Integer> getMouseButtonRelease() {
		return this.mouseButtonRelease;
	}
	
	public EventAction<Integer> getMouseButtonRepeat() {
		return this.mouseButtonRepeat;
	}
	
	public PoisitionAction getMousePosition() {
		return this.mousePosition;
	}
}
