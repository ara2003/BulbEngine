package com.greentree.graphics;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
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

public class GLFWContext  implements AutoCloseable {

	protected final long id;
	
	public long getMonitor(){
		return GLFW.glfwGetWindowMonitor(id);
	}
	
	public GLFWContext(final String title, final int width, final int height, final boolean resizable, final boolean fullscreen, final boolean visible, final GLFWContext share) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable?GLFW.GLFW_TRUE:GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		{
			var shareID = (share == null)?MemoryUtil.NULL:share.id;
		if(fullscreen)
			id = GLFW.glfwCreateWindow(width, height, title, GLFW.glfwGetPrimaryMonitor(), shareID);
		else
			try(MemoryStack stack = MemoryStack.stackPush()) {
				id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, shareID);
				
				final GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
				GLFW.glfwSetWindowPos(id, (mode.width() - width) / 2, (mode.height() - height) / 2);
			}
		}
		if(id == MemoryUtil.NULL) throw new IllegalStateException("Failed to create the BulbGL window " + title + " " + width + "x" + height + " fullscreen: " + fullscreen + " resizable: " + resizable + " visible: " + visible);

		glfwSetErrorCallback((error, description)->System.out.println(error + " " + description));
		
		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwSetWindowSizeCallback(id, (window, width0, height0)-> {
			this.width  = width0;
			this.height = height0;
		});
		this.width  = width;
		this.height = height;

		final GLCapabilities glCapabilities = GL.createCapabilities(false);
		if(null == glCapabilities) throw new IllegalStateException("Failed to load OpenGL native");
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GLFW.glfwSwapInterval(1);
		if(visible)GLFW.glfwShowWindow(id);
		GLFW.glfwMakeContextCurrent(0);
	}
	private int width, height;

	public static long getCurrentContext() {
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
		GLFW.glfwDestroyWindow(id);
	}

	public void getCursorPosition(final double[] x, final double[] y) {
		GLFW.glfwGetCursorPos(id, x, y);
	}

	public int getHeight() {
		return height;
	}

	protected long getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public boolean isCurrent() {
		return GLFW.glfwGetCurrentContext() == id;
	}

	public boolean isShouldClose() {
		return GLFW.glfwWindowShouldClose(id);
	}

	public void makeCurrent() {
		GLFW.glfwMakeContextCurrent(id);
	}

	protected void setCallback(final GLFWCharCallbackI listener) {
		GLFW.glfwSetCharCallback(id, listener);
	}

	protected void setCallback(GLFWCursorPosCallbackI listener) {
		GLFW.glfwSetCursorPosCallback(id, listener);
	}

	protected void setCallback(final GLFWDropCallbackI listener) {
		GLFW.glfwSetDropCallback(id, listener);
	}

	protected void setCallback(final GLFWFramebufferSizeCallbackI listener) {
		GLFW.glfwSetFramebufferSizeCallback(id, listener);
	}

	protected void setCallback(final GLFWKeyCallbackI listener) {
		GLFW.glfwSetKeyCallback(id, listener);
	}

	protected void setCallback(final GLFWMonitorCallbackI listener) {
		GLFW.glfwSetMonitorCallback(listener);
	}

	protected void setCallback(final GLFWMouseButtonCallbackI listener) {
		GLFW.glfwSetMouseButtonCallback(id, listener);
	}

	protected void setCallback(final GLFWScrollCallbackI listener) {
		GLFW.glfwSetScrollCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowCloseCallbackI listener) {
		GLFW.glfwSetWindowCloseCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowContentScaleCallbackI listener) {
		GLFW.glfwSetWindowContentScaleCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowFocusCallbackI listener) {
		GLFW.glfwSetWindowFocusCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowIconifyCallbackI listener) {
		GLFW.glfwSetWindowIconifyCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowMaximizeCallbackI listener) {
		GLFW.glfwSetWindowMaximizeCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowPosCallbackI listener) {
		GLFW.glfwSetWindowPosCallback(id, listener);
	}

	protected void setCallback(final GLFWWindowRefreshCallbackI listener) {
		GLFW.glfwSetWindowRefreshCallback(id, listener);
	}

	public void shouldClose() {
		GLFW.glfwSetWindowShouldClose(id, true);
	}

	public void swapBuffer() {
		GLFW.glfwSwapBuffers(id);
	}

	public static void updateEvents() {
		GLFW.glfwPollEvents();
	}

	public static void unmakeCurrent() {
		GLFW.glfwMakeContextCurrent(0);
	}
}
