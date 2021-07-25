package com.greentree.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

/** @author Arseny Latyshev */
public abstract class BulbGL {

	public static void init(){
		GLFWErrorCallback.createPrint(System.err).set();
		if(!GLFW.glfwInit()) throw new IllegalStateException("Failed to init GLFW");
	}
	
	public static void terminate() {
		GLFW.glfwTerminate();
	}
	
}
