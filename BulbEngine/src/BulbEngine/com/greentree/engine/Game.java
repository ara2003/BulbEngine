package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.engine.bulbgl.Color;
import com.greentree.engine.bulbgl.Graphics;
import com.greentree.engine.bulbgl.OpenGlWindow;
import com.greentree.engine.bulbgl.Window;
import com.greentree.engine.component.Camera;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.Listener;
import com.greentree.engine.input.listeners.Input;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;
import com.greentree.loading.FileSystemLocation;
import com.greentree.loading.ResourceLoader;
import com.greentree.util.Log;
import com.greentree.util.Time;

public final class Game {
	
	private static Builder<?> builder = new BasicXMlBuilder();
	private static GameNode mainNode;
	private static GameClassLoader gameLoader;
	private static SGL GL;
	private static final Object globalLock = new Object();
	private static File root, assets, debug;
	private static boolean running;
	private static List<Class<?>> necessarilyQuery = new CopyOnWriteArrayList<>();
	private static EventSystem eventSystem;
	private static Window window;
	
	private Game() {
	}
	
	public static void addListener(@SuppressWarnings("exports") Listener listener) {
		tryAddNecessarily(listener.getClass());
		getEventSystem().addListener(listener);
	}
	
	public static void event(@SuppressWarnings("exports") Event event) {
		tryAddNecessarily(event.getClass());
		getEventSystem().event(event);
	}
	
	public static void eventNoQueue(@SuppressWarnings("exports") Event event) {
		tryAddNecessarily(event.getClass());
		getEventSystem().eventNoQueue(event);
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
		Graphics.setColor(Color.white);
		window.startRender();
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		Time.updata();
		for(Class<?> clazz : necessarilyQuery) tryAddNecessarily(clazz);
		eventSystem.update();
		Game.mainNode.update();
		window.finishRender();
		Game.GL.flush();
		Graphics.resetTransform();
		if(window.isShouldClose()) {
			Game.running = false;
		}
	}
	
	public static File getAssets() {
		return Game.assets;
	}
	
	public static Builder<?> getBuilder() {
		return builder;
	}
	
	public static GameClassLoader getClassLoader() {
		return gameLoader;
	}
	
	@SuppressWarnings("exports")
	public static EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public static Object getGlobalLock() {
		return Game.globalLock;
	}
	
	public static Camera getMainCamera() {
		return getMainNode().getSystem(RenderSystem.class).getMainCamera();
	}
	
	public static GameNode getMainNode() {
		return mainNode;
	}
	
	public static File getRoot() {
		return Game.root;
	}
	
	public static Class<?> loadClass(final String name) {
		return loadClass(name, new ArrayList<>());
	}
	
	public static Class<?> loadClass(final String name, List<String> packages) {
		try {
			final Class<?> clazz = Game.gameLoader.loadClass(name, packages);
			tryAddNecessarily(clazz);
			return clazz;
		}catch(final ClassNotFoundException e) {
			Log.error("class not found " + e.getMessage());
		}
		return null;
	}
	
	public static void loadScene(final String name) {
		Log.info("Scene load : " + name);
		final InputStream inputStream = ResourceLoader.getResourceAsStream(name + ".scene");
		reset();
		mainNode = new GameNode(getBuilder().getNodeName(inputStream));
		getBuilder().createNode(mainNode, inputStream);
		mainNode.start();
	}
	
	public static void reset() {
		gameLoader = new BasicClassLoader();
		eventSystem = new EventSystem();
		
		window.setEventSystem(eventSystem);
		
		System.gc();
	}
	
	public static void setBuilder(Builder<?> builder) {
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
			e.printStackTrace();
			return;
		}
		int width = Integer.parseInt(properties.getProperty("window.width"));
		int height = Integer.parseInt(properties.getProperty("window.height"));
		boolean fullscreen = Boolean.parseBoolean(properties.getProperty("window.fullscreen"));
		window = new OpenGlWindow(properties.getProperty("window.title", "blub window"), width, height, fullscreen);
		Input.init(window);
		
		Game.running = true;
		Log.checkVerboseLogSetting();
		Game.setup();
		loadScene(properties.getProperty("scene.first"));
		
		while(Game.running) {
			synchronized(Game.globalLock) {
				Game.gameLoop();
			}
		}
		window.close();
		System.exit(0);
	}
	
	private static void tryAddNecessarily(Class<?> clazz) {
		try {
			Game.getMainNode().tryAddNecessarily(clazz);
			necessarilyQuery.remove(clazz);
		}catch(final NullPointerException e) {
			if(!necessarilyQuery.contains(clazz)) necessarilyQuery.add(clazz);
		}
	}

	@SuppressWarnings("exports")
	public static Window getWindow() {
		return window;
	}
}
