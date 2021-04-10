package com.greentree.bulbgl.glfw;


import org.lwjgl.glfw.GLFW;
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
	boolean first = true;
	private int width, height;
	
	public GLFWWindow(final String title, final int width, final int height, final boolean fullscreen) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_RELEASE_BEHAVIOR, GLFW.GLFW_RELEASE_BEHAVIOR_FLUSH);
		// the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		// the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
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
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
//		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glClearColor(.1f, .1f, .2f, 1);
		GLFW.glfwShowWindow(this.id);
		
		GLFW.glfwFocusWindow(this.id);
		
		GLFW.glfwSwapInterval(1);
		GL11.glClearDepth(1.0F);
		
//		GL45.glEnable(GL45.GL_DEBUG_OUTPUT);
//		GL45.glDebugMessageCallback(new GLDebugMessageCallbackI() {
//			
//			@Override
//			public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
//				System.out.printf("%d %d %d %d %d %d %d \n", source, type, id, severity, length, message, userParam);
//			}
//			
//		}, 0);
	}
	
	@Override
	public void close() throws IllegalStateException {
		this.finishRender();
		GLFW.glfwSetWindowShouldClose(this.id, true);
	}
	
	@Override
	public void finishRender() {
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setSize(int width, int height){
		GLFW.glfwSetWindowSize(id, width, height);
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
	
	private void setInput() {
		GLFW.glfwSetWindowSizeCallback(this.id, (window, width, height) -> {
			GLFWWindow.this.width = width;
			GLFWWindow.this.height = height;
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0, width, height, 0.0, 0.0, 1.0);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			GL11.glViewport(0, 0, width, height);
		});
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
		GLFW.glfwSetCursorPosCallback(this.id, (window, xpos, ypos)-> {
			final int x = (int) xpos;
			final int y = (int) ypos;
			if(!GLFWWindow.this.first)
				if(GLFWWindow.this.mouseDraget)
					GLFWWindow.this.eventSystem.event(
						MouseMovedEvent.getInstanse(GLFWWindow.this.eventSystem, MouseMovedEvent.EventType.mouseDragged, x, y, GLFWWindow.this.mouseX, GLFWWindow.this.mouseY));
			else
					GLFWWindow.this.eventSystem.event(
						MouseMovedEvent.getInstanse(GLFWWindow.this.eventSystem, MouseMovedEvent.EventType.mouseMoved, x, y, GLFWWindow.this.mouseX, GLFWWindow.this.mouseY));
			GLFWWindow.this.mouseX = x;
			GLFWWindow.this.mouseY = y;
			GLFWWindow.this.first  = false;
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
	public void setMousePos(final float x, final float y) {
		this.first = true;
		GLFW.glfwSetCursorPos(this.id, (x + 1) / 2 * this.getWidth(), (y + 1) / 2 * this.getHeight());
	}
	
	@Override
	public void startRender() {
		GLFW.glfwSwapBuffers(this.id);
		GLFW.glfwPollEvents();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_ACCUM_BUFFER_BIT);
	}

	@Override
	public void setKeyCallBack(int width, int height) {
	}
}
