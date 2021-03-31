package com.greentree.engine.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.Graphics;
import com.greentree.bulbgl.Window;
import com.greentree.bulbgl.glfw.GLFWWindow;
import com.greentree.bulbgl.input.Input;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.common.Log;
import com.greentree.common.loading.FileSystemLocation;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.time.Time;
import com.greentree.engine.core.editor.Builder;
import com.greentree.engine.core.util.GameProperties;
import com.greentree.event.Event;
import com.greentree.event.EventSystem;
import com.greentree.event.Listener;

public final class Game {
	
	private static Builder builder;
	private static GameScene currentScene;
	private static File root, assets, debug;
	private static EventSystem eventSystem;
	private static Window window;
	private static GameProperties properties;
	
	private Game() {
	}
	
	public static void addListener(final Listener listener) {
		Game.getEventSystem().addListener(listener);
	}
	
	public static GameObject createFromPrefab(final String prefab) {
		return Game.builder.createPrefab(prefab, Game.getCurrentScene());
	}
	
	public static void event(final Event event) {
		Game.getEventSystem().event(event);
	}
	
	public static void exit() {
		Game.window.close();
	}
	
	private static void gameLoop() {
		Graphics.resetTransform();
		Graphics.resetLineWidth();
		Graphics.setAntiAlias(false);
		Game.window.startRender();
		Time.updata();
		Game.eventSystem.update();
		Game.getCurrentScene().update();
		Graphics.setColor(Color.white);
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		Game.window.finishRender();
		Graphics.resetTransform();
	}
	
	public static File getAssets() {
		return Game.assets;
	}
	
	public static Builder getBuilder() {
		return Game.builder;
	}
	
	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(Game.currentScene, "current scene is null");
	}
	
	public static EventSystem getEventSystem() {
		return Game.eventSystem;
	}
	
	public static String getProperty(final String key) {
		return Game.properties.getProperty(key);
	}
	
	public static File getRoot() {
		return Game.root;
	}
	
	public static Window getWindow() {
		return Game.window;
	}
	
	public static Class<?> loadClass(final String name) {
		return Game.loadClass(name, new ArrayList<>());
	}
	
	public static Class<?> loadClass(final String className, final Iterable<String> packageNames) {
		if(packageNames == null) throw new NullPointerException("packages is null");
		if(className == null) throw new NullPointerException("name is null");
		for(final String packageName : packageNames) try {
			return Game.class.getClassLoader().loadClass(packageName + className);
		}catch(final ClassNotFoundException e) {
		}
		Log.error("class not found " + className + ", find in " + packageNames, new ClassNotFoundException(className));
		return null;
	}
	
	public static void loadScene(final String name) {
		Log.info("Scene load : " + name);
		final InputStream inputStream = ResourceLoader.getResourceAsStream(name + ".scene");
		final GameScene   scene       = Game.builder.createScene(inputStream);
		Game.reset(scene);
		Game.builder.fillScene(scene, inputStream);
		scene.start();
	}
	
	private static void reset(final GameScene scene) {
		Game.currentScene = scene;
		Game.eventSystem  = new EventSystem();
		Game.window.setEventSystem(Game.eventSystem);
		Input.setEventSystem(Game.eventSystem);
		System.gc();
	}
	
	public static void setBuilder(final Builder builder) {
		Game.builder = builder;
	}
	
	private static void setup() {
		final int width = Game.window.getWidth(), height = Game.window.getHeight();
		Log.info("Starting display " + width + "x" + height);
		Renderer.get().initDisplay(width, height);
		Renderer.get().enterOrtho(width, height);
		Graphics.init(width, height);
	}
	
	public static void start(final String file, Builder builder) {
		Game.builder = builder;
		Game.root   = new File(file);
		Game.assets = new File(Game.root, "Assets");
		Game.debug  = new File(Game.root, "Debug");
		Log.setLogFolder(Game.debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(Game.assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(Game.root));
		properties = new GameProperties();
		try {
			properties.load(new FileInputStream(new File(Game.root, "config.game")));
		}catch(final IOException e) {
			Log.error(e);
		}
		final int     width      = Integer.parseInt(properties.getProperty("window.width"));
		final int     height     = Integer.parseInt(properties.getProperty("window.height"));
		final boolean fullscreen = Boolean.parseBoolean(properties.getProperty("window.fullscreen"));
		Game.window = new GLFWWindow(properties.getProperty("window.title", "blub window"), width, height, fullscreen);
		Input.setWindow(Game.window);
		Game.setup();
		Game.loadScene(properties.getProperty("scene.first"));
		Input.setEventSystem(Game.eventSystem);
		while(!Game.window.isShouldClose()) Game.gameLoop();
		Game.window.close();
	}

	public static <S extends GameSystem> boolean addSystem(S system) {
		return getCurrentScene().addSystem(system);
	}
	
}
