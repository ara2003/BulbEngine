package com.greentree.graphics;

import org.lwjgl.glfw.GLFW;

/** @author Arseny Latyshev */
public abstract class BulbGL {

	public static void init(){
		if(!GLFW.glfwInit()) throw new IllegalStateException("Failed to init GLFW");
	}
	
	public static void terminate() {
		GLFW.glfwTerminate();
	}
	
}
