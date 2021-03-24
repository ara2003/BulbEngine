package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

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
import com.greentree.engine.component.Camera;
import com.greentree.engine.editor.Builder;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.Listener;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameScene;
import com.greentree.engine.system.RenderSystem;

public final class Game {
	
	private static Builder builder = new BasicXMlBuilder();
	private static GameScene currentScene;
	private static File root, assets, debug;
	private static EventSystem eventSystem;
	private static Window window;
	private static Properties properties;
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	private Game() {
	}
	
	public static void addListener(Listener listener) {
		getEventSystem().addListener(listener);
	}
	
	public static void event(Event event) {
		getEventSystem().event(event);
	}
	
	private static void gameLoop() {
		Graphics.resetTransform();
		Graphics.resetLineWidth();
		Graphics.setAntiAlias(false);
		window.startRender();
		Time.updata();
		eventSystem.update();
		getCurrentScene().update();
		Graphics.setColor(Color.white);
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		window.finishRender();
		Graphics.resetTransform();
	}

	public static File getAssets() {
		return Game.assets;
	}
	
	public static Builder getBuilder() {
		return builder;
	}
	
	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(currentScene, "current scene is null");
	}
	
	public static EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public static Camera getMainCamera() {
		return currentScene.getSystem(RenderSystem.class).getMainCamera();
	}
	
	public static File getRoot() {
		return Game.root;
	}
	
	public static Window getWindow() {
		return window;
	}
	
	public static Class<?> loadClass(final String name) {
		return loadClass(name, new ArrayList<>());
	}
	
	public static Class<?> loadClass(final String className, List<String> packageNames) {
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
		GameScene scene = builder.createScene(inputStream);
		reset(scene);
		builder.fillScene(scene, inputStream);
		scene.start();
	}
	
	private static void reset(GameScene scene) {
		currentScene = scene;
		eventSystem = new EventSystem();
		window.setEventSystem(eventSystem);
		Input.setEventSystem(eventSystem);
		System.gc();
	}
	
	public static void setBuilder(Builder builder) {
		Game.builder = builder;
	}
	
	private static void setup() {
		int width = window.getWidth(), height = window.getHeight();
		Log.info("Starting display " + width + "x" + height);
		Renderer.get().initDisplay(width, height);
		Renderer.get().enterOrtho(width, height);
		Graphics.init(width, height);
	}
	
	public static void start(final String file) {
		root = new File(file);
		assets = new File(root, "Assets");
		debug = new File(root, "Debug");
		Log.setLogFolder(debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(root));
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(root, "config.game")));
		}catch(IOException e) {
			Log.error(e);
		}
		Game.properties = properties;
		{
			int width = Integer.parseInt(properties.getProperty("window.width"));
			int height = Integer.parseInt(properties.getProperty("window.height"));
			boolean fullscreen = Boolean.parseBoolean(properties.getProperty("window.fullscreen"));
			window = new GLFWWindow(properties.getProperty("window.title", "blub window"), width, height, fullscreen);
		}
		Input.setWindow(window);
		Game.setup();
		loadScene(properties.getProperty("scene.first"));
		Input.setEventSystem(eventSystem);
		while(!window.isShouldClose()) {
			Game.gameLoop();
		}
		window.close();
	}

	public static GameObject createFromPrefab(String prefab) {
		return builder.createPrefab(prefab, getCurrentScene());
	}

	public static void exit() {
		window.close();
	}

}
