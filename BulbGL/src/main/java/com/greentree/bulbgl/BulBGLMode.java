package com.greentree.bulbgl;

/** @author Arseny Latyshev */
public interface BulBGLMode {
	
	GraphicsI createGraphics();
	InputI createInput();
	TextureLoaderI createTextureLoader();
	WindowI createWindow(String title, int width, int height, boolean fullscrean);
	ShaderLoaderI createShaderLoader();
	
}
