package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.Graphics;
import com.greentree.bulbgl.Window;
import com.greentree.bulbgl.glfw.GLFWWindow;
import com.greentree.bulbgl.input.Input;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.component.Camera;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.Listener;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameScene;
import com.greentree.engine.system.RenderSystem;
import com.greentree.loading.FileSystemLocation;
import com.greentree.loading.ResourceLoader;
import com.greentree.util.Log;
import com.greentree.util.Time;

public final class Game {
	
	private static Builder builder = new BasicXMlBuilder();
	private static GameScene currentScene;
	private static SGL GL;
	private static File root, assets, debug;
	private static boolean running;
	private static EventSystem eventSystem;
	private static Window window;
	private static Properties properties;
	private static double sleep;
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	private Game() {
	}
	
	@SuppressWarnings("exports")
	public static void addListener(Listener listener) {
		getEventSystem().addListener(listener);
	}
	
	@SuppressWarnings("exports")
	public static void event(Event event) {
		getEventSystem().event(event);
	}
	
	public static void exit() {
		Game.running = false;
	}
	
	private static void gameLoop() {
		Game.GL.glClear(16640);
		Game.GL.glLoadIdentity();
		Graphics.resetTransform();
		Graphics.resetLineWidth();
		Graphics.setAntiAlias(false);
		window.startRender();
		Time.updata();
		eventSystem.update();
		getCurrentScene().update();
//		sync(60);
		Graphics.setColor(Color.white);
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		window.finishRender();
		Game.GL.flush();
		Graphics.resetTransform();
		if(window.isShouldClose()) {
			Game.running = false;
		}
	}
	
	private static void sync(int i) {
		sleep += 1000.0 / i - Time.getDelta();
		try {
			long d = (long) Math.floor(sleep);
			System.out.println(sleep + " " + d);
			sleep -= d;
			Thread.sleep(d);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
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
	
	@SuppressWarnings("exports")
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
		GameScene.Builder sceneBuilder = builder.createSceneBuilder(inputStream);
		reset(sceneBuilder.get());
		builder.fillScene(sceneBuilder, inputStream);
		sceneBuilder.get().init();
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
		Game.GL = Renderer.get();
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
		Game.running = true;
		Game.setup();
		loadScene(properties.getProperty("scene.first"));
		Input.setEventSystem(eventSystem);
		while(Game.running) {
			Game.gameLoop();
		}
		window.close();
		System.exit(0);
	}

	public static <V> void lowTask(Callable<V> callable) {
		
	}

	public static GameObject createObject(String prefab) {
		return builder.createObject(prefab, getCurrentScene());
	}

}
