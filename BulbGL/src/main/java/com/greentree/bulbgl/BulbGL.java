package com.greentree.bulbgl;

/** @author Arseny Latyshev */
public class BulbGL {
	
	private static BulBGLMode mode;
	private static GraphicsI graphics;
	private static TextureLoaderI textureLoader;
	private static InputI input;
	private static boolean cWindow, initialize;
	private static ShaderLoaderI shaderLoader;
	
	private BulbGL() {
	}
	
	public static TextureLoaderI getTextureLoader() {
		if(BulbGL.textureLoader == null) BulbGL.textureLoader = BulbGL.mode.createTextureLoader();
		return textureLoader;
	}
	
	public static InputI getInput() {
		if(BulbGL.input == null) BulbGL.input = BulbGL.mode.createInput();
		return input;
	}
	
	public static WindowI getWindow(final String title, final int width, final int height, final boolean fullscrean) {
		if(BulbGL.cWindow) throw new UnsupportedOperationException("seconde window create");
		BulbGL.cWindow = true;
		return BulbGL.mode.createWindow(title, width, height, fullscrean);
	}
	
	public static GraphicsI getGraphics() {
		if(BulbGL.graphics == null) BulbGL.graphics = BulbGL.mode.createGraphics();
		return graphics;
	}
	
	public static void init(final BulBGLMode mode) {
		if(BulbGL.initialize) throw new UnsupportedOperationException("seconde initialize");
		BulbGL.initialize = true;
		BulbGL.mode       = mode;
	}

	public static ShaderLoaderI getShaderLoader() {
		if(BulbGL.shaderLoader == null) BulbGL.shaderLoader = BulbGL.mode.createShaderLoader();
		return shaderLoader;
	}
	
}
