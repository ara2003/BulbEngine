package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.greentree.engine.component.Camera;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.gui.ui.Button;
import com.greentree.engine.input.Input;
import com.greentree.engine.loading.FileSystemLocation;
import com.greentree.engine.loading.ResourceLoader;
import com.greentree.engine.opengl.InternalTextureLoader;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.engine.system.RenderSystem;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Game {
	
	private static Builder builer = new BasicXMlBuilder();
	private static Scene currentScene;
	private static ClassLoader gameLoader;
	private static SGL GL;
	private static final Object globalCock = new Object();
	private static Thread mianGameLoop;
	private static DisplayMode originalDisplayMode;
	private static File root, assets;
	private static boolean running;
	private static DisplayMode targetDisplayMode;
	private static int width, height;
	
	private Game() {
	}
	
	public static void addListener(Listener listener) {
		currentScene.tryAddNecessarily(listener.getClass());
		currentScene.getEventSystem().addListener(listener);
	}
	
	public static void event(Event event) {
		currentScene.getEventSystem().event(event);
	}
	
	public static void eventNoQueue(Event event) {
		currentScene.getEventSystem().eventNoQueue(event);
	}
	
	public static void exit() {
		Game.running = false;
	}
	
	private static void gameLoop() {
		Input.poll(Game.width, Game.height);
		Game.GL.glClear(16640);
		Game.GL.glLoadIdentity();
		Graphics.resetTransform();
		Graphics.resetLineWidth();
		Graphics.setAntiAlias(false);
		Graphics.setColor(Color.white);
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
		Time.updata();
		Game.currentScene.update();
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
	
	public static Scene getCurrentScene() {
		return currentScene;
	}
	
	public static Object getGlobalCock() {
		return Game.globalCock;
	}
	
	public static Camera getMainCamera() {
		return getCurrentScene().getSystem(RenderSystem.class).getMainCamera();
	}
	
	public static File getRoot() {
		return Game.root;
	}
	
	private static boolean isFullscreen() {
		return Display.isFullscreen();
	}
	
	public static Class<?> loadClass(final String name) {
		try {
			final Class<?> clazz = Game.gameLoader.loadClass(name);
			try {
				Game.getCurrentScene().tryAddNecessarily(clazz);
			}catch(final NullPointerException e) {
			}
			return clazz;
		}catch(final ClassNotFoundException e) {
			Log.warn(e);
		}
		return null;
	}
	
	public static void loadScene(final String name) {
		final InputStream in = ResourceLoader.getResourceAsStream(Game.getAssets().getName() + "\\" + name + ".scene");
		Log.info("Scene load : " + name);
		synchronized(Game.globalCock) {
			Game.currentScene = builer.createScene(in);
			Game.reset();
		}
	}
	
	public static void reset() {
		gameLoader = new BasicClassLoader(
				new String[]{	Transform.class.getPackageName(),
						ColliderSystem.class.getPackageName(),
						ColliderComponent.class.getPackageName(),
						Button.class.getPackageName()});
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
		Game.mianGameLoop = new Thread(()-> {
			Game.GL = Renderer.get();
			Game.reset();
			{
				boolean fullscreen = false;
				int width = 800, height = 600;
				String firstScene = "";
				{
					Scanner in = null;
					try {
						in = new Scanner(new FileInputStream(file + "\\config.game"));
					}catch(final FileNotFoundException e1) {
						e1.printStackTrace();
						return;
					}
					Display.sync(60);
					while(in.hasNext()) {
						final String a = in.next();
						final String[] words = a.replace(' ', '=').split("=");
						if(words[0].equals("firstScene")) {
							firstScene = words[1];
						}
						if(words[0].equals("width")) {
							width = Integer.parseInt(words[1]);
						}
						if(words[0].equals("height")) {
							height = Integer.parseInt(words[1]);
						}
						if(words[0].equals("fullscreen")) {
							fullscreen = Boolean.parseBoolean(words[1]);
						}
						if(words[0].equals("targetFPS")) {
							Display.sync(Integer.parseInt(words[1]));
						}
						if(words[0].equals("title")) {
							Display.setTitle(words[1]);
						}
					}
					in.close();
					Game.running = true;
				}
				Log.checkVerboseLogSetting();
				Game.originalDisplayMode = Display.getDisplayMode();
				Game.setDisplayMode(width, height, fullscreen);
				Game.setup();
				loadScene(firstScene);
			}
			while(Game.running) {
				synchronized(Game.globalCock) {
					Game.gameLoop();
				}
			}
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}, "Mian Game Loop");
		Game.mianGameLoop.start();
	}
}
