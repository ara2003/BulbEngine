package com.greentree.bulbgl.glfw;


import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.greentree.bulbgl.WindowI;
import com.greentree.bulbgl.input.event.KeyPressedEvent;
import com.greentree.bulbgl.input.event.KeyRepaeasedEvent;
import com.greentree.bulbgl.input.event.KeyRepeatedEvent;
import com.greentree.bulbgl.input.event.MouseClickEvent;
import com.greentree.bulbgl.input.event.MouseMovedEvent;
import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public class GLFWWindow extends WindowI {
	
	private final long id;
	private int mouseY, mouseX;
	private boolean mouseDraget;
	private EventSystem eventSystem;
	
	public GLFWWindow(final String title, final int width, final int height, final boolean fullscreen) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_RELEASE_BEHAVIOR, GLFW.GLFW_RELEASE_BEHAVIOR_FLUSH);
		// the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		// the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, GLFW.GLFW_NATIVE_CONTEXT_API);
		this.id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(this.id == MemoryUtil.NULL) throw new IllegalStateException("Failed to create the GLFW window");
		if(fullscreen) GLFW.glfwSetWindowMonitor(this.id, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, GLFW.GLFW_REFRESH_RATE);
		else // Get the thread stack and push a new frame
			try(MemoryStack stack = MemoryStack.stackPush()) {
				// Get the resolution of the primary monitor
				final GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
				// Center the window
				GLFW.glfwSetWindowPos(this.id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
			} // the stack frame is popped automatically
		GLFW.glfwMakeContextCurrent(this.id);
		//input
		this.setInput();
		// load OpenGL native
		final GLCapabilities glCapabilities = GL.createCapabilities(false);
		if(null == glCapabilities) throw new IllegalStateException("Failed to load OpenGL native");
		// Enable depth testing for z-culling
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		// Set the type of depth-test
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		// Enable smooth shading
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glClearColor(.1f, .1f, .2f, 1);
		GLFW.glfwShowWindow(this.id);
		
		GLFW.glfwFocusWindow(this.id);
		
		GLFW.glfwSwapInterval(1);
	}
	
	@Override
	public void close() throws IllegalStateException {
		this.finishRender();
		GLFW.glfwSetWindowShouldClose(this.id, true);
	}
	
	@Override
	public void finishRender() {
		GLFW.glfwSwapBuffers(this.id);
		GLFW.glfwPollEvents();
	}
	
	@Override
	public int getHeight() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			final IntBuffer pHeight = stack.mallocInt(1);
			GLFW.glfwGetFramebufferSize(this.id, null, pHeight);
			return pHeight.get(0);
		}
	}
	
	@Override
	public int getWidth() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			final IntBuffer pWidth = stack.mallocInt(1);
			GLFW.glfwGetFramebufferSize(this.id, pWidth, null);
			return pWidth.get(0);
		}
	}
	
	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public boolean isShouldClose() {
		return GLFW.glfwWindowShouldClose(this.id);
	}
	
	@Override
	public void setEventSystem(final EventSystem eventSystem) {
		this.eventSystem = eventSystem;
	}
	
			boolean first = true;
	private void setInput() {
		
		
		GLFW.glfwSetKeyCallback(this.id, new GLFWKeyCallback() {
			
			@Override
			public void invoke(final long window, final int key, final int scancode, final int action, final int mods) {
				switch(action) {
					case GLFW.GLFW_PRESS -> GLFWWindow.this.eventSystem.event(KeyPressedEvent.getInstanse(GLFWWindow.this.eventSystem, key));
					case GLFW.GLFW_RELEASE -> GLFWWindow.this.eventSystem.event(KeyRepaeasedEvent.getInstanse(GLFWWindow.this.eventSystem, key));
					case GLFW.GLFW_REPEAT -> GLFWWindow.this.eventSystem.event(KeyRepeatedEvent.getInstanse(GLFWWindow.this.eventSystem, key));
				}
			}
		});
		GLFW.glfwSetCursorPosCallback(this.id, new GLFWCursorPosCallbackI() {
			
			
			@Override
			public void invoke(final long window, final double xpos, final double ypos) {
				final int x = (int) xpos;
				final int y = (int) ypos;
				if(!first)
					if(GLFWWindow.this.mouseDraget)
						GLFWWindow.this.eventSystem.event(
							MouseMovedEvent.getInstanse(GLFWWindow.this.eventSystem, MouseMovedEvent.EventType.mouseDragged, x, y, GLFWWindow.this.mouseX, GLFWWindow.this.mouseY));
				else
						GLFWWindow.this.eventSystem.event(
							MouseMovedEvent.getInstanse(GLFWWindow.this.eventSystem, MouseMovedEvent.EventType.mouseMoved, x, y, GLFWWindow.this.mouseX, GLFWWindow.this.mouseY));
				GLFWWindow.this.mouseX = x;
				GLFWWindow.this.mouseY = y;
				first             = false;
			}
		});
		GLFW.glfwSetMouseButtonCallback(this.id, (window, button, action, mods)-> {
			this.mouseDraget = action != GLFW.GLFW_RELEASE;
			switch(action) {
				case GLFW.GLFW_PRESS -> this.eventSystem
					.event(MouseClickEvent.getInstanse(this.eventSystem, MouseClickEvent.EventType.mousePress, button, this.mouseX, this.mouseY));
				case GLFW.GLFW_RELEASE -> this.eventSystem
					.event(MouseClickEvent.getInstanse(this.eventSystem, MouseClickEvent.EventType.mouseRelease, button, this.mouseX, this.mouseY));
				case GLFW.GLFW_REPEAT -> this.eventSystem
					.event(MouseClickEvent.getInstanse(this.eventSystem, MouseClickEvent.EventType.mouseRepeat, button, this.mouseX, this.mouseY));
			}
		});
	}
	
	@Override
	public void startRender() {
		GL11.glClear(GL11.GL_ACCUM_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearDepth(1.0F);
		final int w[] = {0};
		final int h[] = {0};
		GLFW.glfwGetFramebufferSize(this.id, w, h);
		GL11.glViewport(0, 0, w[0], h[0]);
	}

	@Override
	public void setMousePos(float x, float y) {
		first = true;
		GLFW.glfwSetCursorPos(id, (x+1)/2 * getWidth(), (y+1)/2 * getHeight());
	}
}
