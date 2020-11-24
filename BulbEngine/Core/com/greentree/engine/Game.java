package com.greentree.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.DisplayMode;

import com.greentree.engine.event.EventSystem;
import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.input.Input;
import com.greentree.engine.object.Scene;
import com.greentree.opengl.InternalTextureLoader;
import com.greentree.opengl.rendener.Renderer;
import com.greentree.opengl.rendener.SGL;

public final class Game {
	
	private static Scene currentScene;
	private static ClassLoader gameLoader;
	private static SGL GL;
	private static final Object globalCock = new Object();
	private static Thread mianGameLoop;
	private static DisplayMode originalDisplayMode;
	private static File root;
	private static boolean running;
	private static DisplayMode targetDisplayMode;
	private static Map<Class<?>, FileWriter> writer = new HashMap<>();
	private static int width, height;

	private Game() {
	}

	public static void addTime(final Class<?> clazz, final long time) {
		if(clazz == null) Log.warn("class is null");
		FileWriter writer = Game.writer.get(clazz);
		if(writer == null) {
			final File folder = new File(root, "debug");
			final File file = new File(folder, clazz.getSimpleName() + ".txt");
			if(!file.exists()) try {
				file.createNewFile();
			}catch(final IOException e) {
				Log.error(e);
				return;
			}
			try {
				writer = new FileWriter(file);
			}catch(final IOException e) {
				e.printStackTrace();
			}
			Game.writer.put(clazz, writer);
		}
		if(writer == null) Log.error("writer is null");
		try {
			writer.write(Time.getTime() + " " + time + "\n");
			writer.flush();
		}catch(final IOException e) {
		}
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
		if(Display.isCloseRequested()) Game.running = false;
	}

	public static EventSystem getEventSystem() {
		return Game.currentScene.getEventSystem();
	}
	
	public static Object getGlobalCock() {
		return Game.globalCock;
	}

	public static File getRoot() {
		return Game.root;
	}
	
	private static boolean isFullscreen() {
		return Display.isFullscreen();
	}

	public static Class<?> loadClass(final String name) throws ClassNotFoundException {
		return Game.gameLoader.loadClass(name);
	}

	public static void reset() {
		if(Game.gameLoader == null) Game.gameLoader = new BasicClassLoader();
	}

	private static void setDisplayMode(final int width, final int height, final boolean fullscreen) {
		if(Game.width == width && Game.height == height && Game.isFullscreen() == fullscreen) return;
		try {
			Game.targetDisplayMode = null;
			if(fullscreen) {
				final DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				for(final DisplayMode current : modes)
					if(current.getWidth() == width && current.getHeight() == height) {
						if((Game.targetDisplayMode == null || current.getFrequency() >= freq)
								&& (Game.targetDisplayMode == null
										|| current.getBitsPerPixel() > Game.targetDisplayMode.getBitsPerPixel())) {
							Game.targetDisplayMode = current;
							freq = Game.targetDisplayMode.getFrequency();
						}
						if(current.getBitsPerPixel() == Game.originalDisplayMode.getBitsPerPixel()
								&& current.getFrequency() == Game.originalDisplayMode.getFrequency()) {
							Game.targetDisplayMode = current;
							break;
						}
					}
			}else Game.targetDisplayMode = new DisplayMode(width, height);
			if(Game.targetDisplayMode == null)
				Log.error("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
			Game.width = width;
			Game.height = height;
			Display.setDisplayMode(Game.targetDisplayMode);
			Display.setFullscreen(fullscreen);
			if(Game.targetDisplayMode.getBitsPerPixel() == 16) InternalTextureLoader.get().set16BitMode();
		}catch(final LWJGLException e) {
			Log.error("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen, e);
		}
	}

	public static void setScene(final Scene scene) {
		if(!scene.equals(Game.currentScene)) synchronized(Game.globalCock) {
			Game.currentScene = scene;
			Game.reset();
		}
	}

	private static void setup() {
		if(Game.targetDisplayMode == null) Game.setDisplayMode(640, 480, false);
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
		if(!Display.isCreated()) Log.error("Failed to initialise the LWJGL display");
		Log.info("Starting display " + Game.width + "x" + Game.height);
		Renderer.get().initDisplay(Game.width, Game.height);
		Renderer.get().enterOrtho(Game.width, Game.height);
		Graphics.init(Game.width, Game.height);
	}

	public static void start(final String file) {
		Game.root = new File(file);
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
						in = new Scanner(new FileInputStream(file + "//config.game"));
					}catch(final FileNotFoundException e1) {
						e1.printStackTrace();
						return;
					}
					Display.sync(60);
					while(in.hasNext()) {
						final String a = in.next();
						final String[] words = a.replace(' ', '=').split("=");
						if(words[0].equals("firstScene")) firstScene = words[1];
						if(words[0].equals("width")) width = Integer.parseInt(words[1]);
						if(words[0].equals("height")) height = Integer.parseInt(words[1]);
						if(words[0].equals("fullscreen")) fullscreen = Boolean.parseBoolean(words[1]);
						if(words[0].equals("targetFPS")) Display.sync(Integer.parseInt(words[1]));
						if(words[0].equals("title")) Display.setTitle(words[1]);
					}
					in.close();
					Game.running = true;
				}
				Log.checkVerboseLogSetting();
				Game.originalDisplayMode = Display.getDisplayMode();
				Game.setDisplayMode(width, height, fullscreen);
				Game.setup();
				Game.currentScene = SceneManager.getScene(firstScene);
			}
			while(Game.running) synchronized(Game.globalCock) {
				Game.gameLoop();
			}
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}, "Mian Game Loop");
		Game.mianGameLoop.start();
	}
}
