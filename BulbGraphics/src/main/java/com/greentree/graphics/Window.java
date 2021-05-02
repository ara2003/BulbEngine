package com.greentree.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/** @author Arseny Latyshev */
public class Window implements AutoCloseable {
	
	
	private final long id;
	private int width, height;
	
	public Window(final String title, final int width, final int height, final boolean resizable, final boolean fullscreen) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE);
		if(resizable) GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		else GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		
		this.id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(this.id == MemoryUtil.NULL) throw new IllegalStateException("Failed to create the GLFW window " + title);
		
		GLFW.glfwMakeContextCurrent(this.id);
		GLFW.glfwSetWindowSizeCallback(this.id, (window, width0, height0)-> {
			if(window != this.id) return;
			this.width  = width0;
			this.height = height0;
			GL11.glViewport(0, 0, width0, height0);
		});
		this.width  = width;
		this.height = height;
		
		if(fullscreen) GLFW.glfwSetWindowMonitor(this.id, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, GLFW.GLFW_REFRESH_RATE);
		else
			try(MemoryStack stack = MemoryStack.stackPush()) {
				final GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
				GLFW.glfwSetWindowPos(this.id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
			}
		
		final GLCapabilities glCapabilities = GL.createCapabilities(false);
		if(null == glCapabilities) throw new IllegalStateException("Failed to load OpenGL native");
		
		GLFW.glfwShowWindow(this.id);
		GLFW.glfwFocusWindow(this.id);
		GLFW.glfwSwapInterval(0);
		GLFW.glfwMakeContextCurrent(0);
	}
	
	public static long getCurrent() {
		return GLFW.glfwGetCurrentContext();
	}
	
	protected static void setCallback(final GLFWErrorCallbackI listener) {
		GLFW.glfwSetErrorCallback(listener);
	}
	
	protected static void setCallback(final GLFWJoystickCallbackI listener) {
		GLFW.glfwSetJoystickCallback(listener);
	}
	
	@Override
	public void close() {
		GLFW.glfwDestroyWindow(this.id);
	}
	
	public void getCursorPosition(final double[] x, final double[] y) {
		GLFW.glfwGetCursorPos(this.id, x, y);
	}
	
	public int getHeight() {
		return this.height;
	}
	
	protected long getId() {
		return this.id;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public boolean isCurrent() {
		return GLFW.glfwGetCurrentContext() == this.id;
	}
	
	public void shouldClose() { 
		GLFW.glfwSetWindowShouldClose(this.id, true);
	}
	
	public boolean isShouldClose() { 
		return GLFW.glfwWindowShouldClose(this.id);
	}
	
	public void makeCurrent() {
		GLFW.glfwMakeContextCurrent(this.id);
	}
	
	protected void setCallback(final GLFWDropCallbackI listener) {
		GLFW.glfwSetDropCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWFramebufferSizeCallbackI listener) {
		GLFW.glfwSetFramebufferSizeCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWKeyCallbackI listener) {
		GLFW.glfwSetKeyCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWMonitorCallbackI listener) {
		GLFW.glfwSetMonitorCallback(listener);
	}
	
	protected void setCallback(final GLFWMouseButtonCallbackI listener) {
		GLFW.glfwSetMouseButtonCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWScrollCallbackI listener) {
		GLFW.glfwSetScrollCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowCloseCallbackI listener) {
		GLFW.glfwSetWindowCloseCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowContentScaleCallbackI listener) {
		GLFW.glfwSetWindowContentScaleCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowFocusCallbackI listener) {
		GLFW.glfwSetWindowFocusCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowIconifyCallbackI listener) {
		GLFW.glfwSetWindowIconifyCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowMaximizeCallbackI listener) {
		GLFW.glfwSetWindowMaximizeCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowPosCallbackI listener) {
		GLFW.glfwSetWindowPosCallback(this.id, listener);
	}
	
	protected void setCallback(final GLFWWindowRefreshCallbackI listener) {
		GLFW.glfwSetWindowRefreshCallback(this.id, listener);
	}
	
	//	protected void setCallback(final GLFWWindowSizeCallbackI listener) {
	//		GLFW.glfwSetWindowSizeCallback(this.id, listener);
	//	}
	
	public void swapBuffer() {
		GLFW.glfwSwapBuffers(this.id);
	}
	
	public void updateEvents() {
		GLFW.glfwPollEvents();
	}
	
	public class Builder {
		
		public void setResizable(final boolean resizable) {
			
		}
	}

	protected void setCallback(GLFWCursorPosCallbackI listener) {
		GLFW.glfwSetCursorPosCallback(id, listener);
	}
	
}
