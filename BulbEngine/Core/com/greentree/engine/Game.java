package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.greentree.engine.component.Camera;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.Listener;
import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.input.Input;
import com.greentree.engine.loading.FileSystemLocation;
import com.greentree.engine.loading.ResourceLoader;
import com.greentree.engine.opengl.InternalTextureLoader;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Game {
	
	
	private static Builder<?> builder = new BasicXMlBuilder();
	private static GameNode mainNode;
	private static GameClassLoader gameLoader;
	private static SGL GL;
	private static final Object globalLock = new Object();
	private static Thread mianGameLoop;
	private static DisplayMode originalDisplayMode;
	private static File root, assets;
	private static boolean running;
	private static DisplayMode targetDisplayMode;
	private static int width, height;
	private static List<Class<?>> necessarilyQuery = new CopyOnWriteArrayList<>();
	private static EventSystem eventSystem;
	
	private Game() {
	}
	
	public static EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public static void addListener(Listener listener) {
		tryAddNecessarily(listener.getClass());
		getEventSystem().addListener(listener);
	}
	
	public static void event(Event event) {
		tryAddNecessarily(event.getClass());
		getEventSystem().event(event);
	}
	
	public static void eventNoQueue(Event event) {
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
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		Time.updata();
		for(Class<?> clazz : necessarilyQuery)tryAddNecessarily(clazz);
		Input.poll(Game.width, Game.height);
		eventSystem.update();
		Game.mainNode.update();
		Game.GL.flush();
		Graphics.resetTransform();
		Display.update(true);
		if(Display.isCloseRequested()) {
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
	
	public static GameNode getMainNode() {
		return mainNode;
	}
	
	public static Object getGlobalLock() {
		return Game.globalLock;
	}
	
	public static Camera getMainCamera() {
		return getMainNode().getSystem(RenderSystem.class).getMainCamera();
	}
	
	public static File getRoot() {
		return Game.root;
	}
	
	private static boolean isFullscreen() {
		return Display.isFullscreen();
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
	public static Class<?> loadClass(final String name) {
		return loadClass(name, new ArrayList<>());
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
	}
	
	public static void setBuilder(Builder<?> builder) {
		Game.builder = builder;
	}
	
	private static void setDisplayMode(final int width, final int height, final boolean fullscreen) {
		if((Game.width == width) && (Game.height == height) && (Game.isFullscreen() == fullscreen)) return;
		try {
			Game.targetDisplayMode = null;
			if(fullscreen) {
				final DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				for(final DisplayMode current : modes)
					if((current.getWidth() == width) && (current.getHeight() == height)) {
						if(((Game.targetDisplayMode == null) || (current.getFrequency() >= freq))
								&& ((Game.targetDisplayMode == null)
										|| (current.getBitsPerPixel() > Game.targetDisplayMode.getBitsPerPixel()))) {
							Game.targetDisplayMode = current;
							freq = Game.targetDisplayMode.getFrequency();
						}
						if((current.getBitsPerPixel() == Game.originalDisplayMode.getBitsPerPixel())
								&& (current.getFrequency() == Game.originalDisplayMode.getFrequency())) {
							Game.targetDisplayMode = current;
							break;
						}
					}
			}else {
				Game.targetDisplayMode = new DisplayMode(width, height);
			}
			if(Game.targetDisplayMode == null) {
				Log.error("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
			}
			Game.width = width;
			Game.height = height;
			Display.setDisplayMode(Game.targetDisplayMode);
			Display.setFullscreen(fullscreen);
			if(Game.targetDisplayMode.getBitsPerPixel() == 16) {
				InternalTextureLoader.get().set16BitMode();
			}
		}catch(final LWJGLException e) {
			Log.error("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen, e);
		}
	}
	
	private static void setup() {
		if(Game.targetDisplayMode == null) {
			Game.setDisplayMode(640, 480, false);
		}
		Log.info("LWJGL Version: " + Sys.getVersion());
		Log.info("OriginalDisplayMode: " + Game.originalDisplayMode);
		Log.info("TargetDisplayMode: " + Game.targetDisplayMode);
		try {
			final PixelFormat format1 = new PixelFormat(8, 8, 0, 0);
			Display.create(format1);
		}catch(final Exception e) {
			Display.destroy();
			try {
				final PixelFormat format2 = new PixelFormat(8, 8, 0);
				Display.create(format2);
			}catch(final Exception e2) {
				Display.destroy();
				try {
					Display.create(new PixelFormat());
				}catch(final Exception e3) {
					Log.error(e3);
				}
			}
		}
		if(!Display.isCreated()) {
			Log.error("Failed to initialise the LWJGL display");
		}
		Log.info("Starting display " + Game.width + "x" + Game.height);
		Renderer.get().initDisplay(Game.width, Game.height);
		Renderer.get().enterOrtho(Game.width, Game.height);
		Graphics.init(Game.width, Game.height);
	}
	
	public static void start(final String file) {
		root = new File(file);
		assets = new File(root, "Assets");
		ResourceLoader.addResourceLocation(new FileSystemLocation(assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(root));
		Game.mianGameLoop = new Thread(()-> {
			Game.GL = Renderer.get();
			Game.reset();
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(new File(root, "config.game")));
			}catch(IOException e) {
				e.printStackTrace();
				return;
			}
			Display.setTitle(properties.getProperty("window.title", "blub window"));
			int width = Integer.parseInt(properties.getProperty("window.width"));
			int height = Integer.parseInt(properties.getProperty("window.height"));
			boolean fullscreen = Boolean.parseBoolean(properties.getProperty("window.fullscreen"));
			Game.running = true;
			Log.checkVerboseLogSetting();
			Game.originalDisplayMode = Display.getDisplayMode();
			Game.setDisplayMode(width, height, fullscreen);
			Game.setup();
			loadScene(properties.getProperty("scene.first"));
			while(Game.running) {
				synchronized(Game.globalLock) {
					Game.gameLoop();
				}
			}
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}, "Mian Game Loop");
		Game.mianGameLoop.start();
	}
	
	private static void tryAddNecessarily(Class<?> clazz) {
		try {
			Game.getMainNode().tryAddNecessarily(clazz);
			necessarilyQuery.remove(clazz);
		}catch(final NullPointerException e) {
			if(!necessarilyQuery.contains(clazz))
				necessarilyQuery.add(clazz);
		}
	}
	
}
