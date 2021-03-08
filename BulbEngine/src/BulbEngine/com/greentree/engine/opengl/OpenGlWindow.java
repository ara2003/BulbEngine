package com.greentree.engine.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.greentree.engine.Window;
import com.greentree.engine.input.KeyListener;
import com.greentree.engine.input.MouseListener;

/** @author Arseny Latyshev */
public class OpenGlWindow extends Window {
	
	private final long id;
	private int mouseY, mouseX;
	
	public OpenGlWindow(String title, int width, int height, boolean fullscreen, KeyListener keyListener,
			MouseListener mouseListener) {
		GLFW.glfwInit();
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_RELEASE_BEHAVIOR, GLFW_RELEASE_BEHAVIOR_FLUSH);
		
		// the window will stay hidden after creation
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		// the window will be resizable
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_NATIVE_CONTEXT_API);
		id = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(id == MemoryUtil.NULL) {
			throw new IllegalStateException("Failed to create the GLFW window");
		}
		glfwSetKeyCallback(id, new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action != 0) keyListener.keyPressed(key);
				else keyListener.keyReleased(key);
				if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
					glfwSetWindowShouldClose(window, true);
				}
			}
		});
		GLFW.glfwSetCursorPosCallback(id, (window, xpos, ypos)-> {
			int x = (int) xpos;
			int y = (int) ypos;
			mouseListener.mouseMoved(mouseX, mouseY, x, y);
			mouseX = x;
			mouseY = y;
		});
		GLFW.glfwSetMouseButtonCallback(id, (window, button, action, mods)-> {
			if(action != 0) mouseListener.mousePressed(button, mouseX, mouseY);
			else mouseListener.mouseReleased(button, mouseX, mouseY);
		});
		
		
		
		if(fullscreen)
			glfwSetWindowMonitor(id, glfwGetPrimaryMonitor(), 0, 0, width, height, GLFW_REFRESH_RATE);
		else {
		// Get the thread stack and push a new frame
		try(MemoryStack stack = MemoryStack.stackPush()) {
			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			// Center the window
			glfwSetWindowPos(id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		} // the stack frame is popped automatically
		}
		glfwMakeContextCurrent(id);
		// load OpenGL native
		GLCapabilities glCapabilities = GL.createCapabilities(false);
		if(null == glCapabilities) {
			throw new IllegalStateException("Failed to load OpenGL native");
		}
		// Enable depth testing for z-culling
		glEnable(GL_DEPTH_TEST);
		// Set the type of depth-test
		glDepthFunc(GL_LEQUAL);
		// Enable smooth shading
		glShadeModel(GL_SMOOTH);
		glCullFace(GL_FRONT);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glClearColor(.1f, .1f, .2f, 1);
		glfwShowWindow(id);
	}
	
	@Override
	public void close() throws IllegalStateException {
		glfwDestroyWindow(id);
	}
	
	@Override
	public void finishRender() {
		glfwSwapBuffers(id);
		glfwPollEvents();
	}
	
	@Override
	public int getHeight() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetFramebufferSize(id, null, pHeight);
			return pHeight.get(0);
		}
	}
	
	@Override
	public int getIndexOf(String key) {
		return switch(key) {
			case "Q" -> GLFW.GLFW_KEY_Q;
			case "W" -> GLFW.GLFW_KEY_W;
			case "E" -> GLFW.GLFW_KEY_E;
			case "R" -> GLFW.GLFW_KEY_R;
			case "T" -> GLFW.GLFW_KEY_T;
			case "Y" -> GLFW.GLFW_KEY_Y;
			case "U" -> GLFW.GLFW_KEY_U;
			case "I" -> GLFW.GLFW_KEY_I;
			case "O" -> GLFW.GLFW_KEY_O;
			case "P" -> GLFW.GLFW_KEY_P;
			case "A" -> GLFW.GLFW_KEY_A;
			case "S" -> GLFW.GLFW_KEY_S;
			case "D" -> GLFW.GLFW_KEY_D;
			case "F" -> GLFW.GLFW_KEY_F;
			case "G" -> GLFW.GLFW_KEY_G;
			case "H" -> GLFW.GLFW_KEY_H;
			case "J" -> GLFW.GLFW_KEY_J;
			case "K" -> GLFW.GLFW_KEY_K;
			case "L" -> GLFW.GLFW_KEY_L;
			case "Z" -> GLFW.GLFW_KEY_Z;
			case "X" -> GLFW.GLFW_KEY_X;
			case "C" -> GLFW.GLFW_KEY_C;
			case "V" -> GLFW.GLFW_KEY_V;
			case "B" -> GLFW.GLFW_KEY_B;
			case "N" -> GLFW.GLFW_KEY_N;
			case "M" -> GLFW.GLFW_KEY_M;
			case "ESCAPE" -> GLFW.GLFW_KEY_ESCAPE;
			case "SPACE" -> GLFW.GLFW_KEY_SPACE;
			default -> -1;
		};
	}
	
	@Override
	public int getWidth() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			glfwGetFramebufferSize(id, pWidth, null);
			return pWidth.get(0);
		}
	}
	
	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public boolean isKeyDown(int key) {
		return glfwGetKey(id, key) != 0;
	}
	
	@Override
	public boolean isShouldClose() {
		return glfwWindowShouldClose(id);
	}
	
	@Override
	public void startRender() {
		glClear(GL_ACCUM_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glClearDepth(1.0F);
		int w[] = {0};
		int h[] = {0};
		glfwGetFramebufferSize(id, w, h);
		glViewport(0, 0, w[0], h[0]);
	}
}
