package com.greentree.bulbgl.glfw;

import org.lwjgl.glfw.GLFW;

import com.greentree.bulbgl.BulBGLMode;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.InputI;
import com.greentree.bulbgl.ShaderLoaderI;
import com.greentree.bulbgl.TextureLoaderI;
import com.greentree.bulbgl.WindowI;
import com.greentree.bulbgl.opengl.GLGraphics;
import com.greentree.bulbgl.opengl.GLTextureLoader;
import com.greentree.bulbgl.opengl.shader.GLShaderLoader;

/**
 * @author Arseny Latyshev
 *
 */
public class BulBGLFWMode implements BulBGLMode {
	
	public BulBGLFWMode() {
		GLFW.glfwInit();
	}
	
	@Override
	public WindowI createWindow(String title, int width, int height, boolean fullscrean) {
		return new GLFWWindow(title, width, height, fullscrean);
	}

	@Override
	public InputI createInput() {
		return new GLFWInput();
	}

	@Override
	public GraphicsI createGraphics() {
		return new GLGraphics();
	}

	@Override
	public TextureLoaderI createTextureLoader() {
		return new GLTextureLoader();
	}

	@Override
	public ShaderLoaderI createShaderLoader() {
		return new GLShaderLoader();
	}
	
}
