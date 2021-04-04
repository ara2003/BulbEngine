package com.greentree.engine.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import com.greentree.common.Log;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.time.Time;
import com.greentree.engine.core.editor.Builder;

public final class Game {
	
	private static Builder builder;
	private static GameScene currentScene;
	
	private Game() {
	}
	
	public static void exit() {
		System.exit(0);
	}
	
	public static void gameLoop() {
		Time.updata();
		Events.update();
		Game.getCurrentScene().update();
	}

	public static GameObject createFromPrefab(final String prefab) {
		return Game.builder.createPrefab(prefab, Game.getCurrentScene());
	}
	
	
	public static Builder getBuilder() {
		return Game.builder;
	}
	
	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(Game.currentScene, "current scene is null");
	}
	
	
	public static Class<?> loadClass(final String name) {
		return Game.loadClass(name, new ArrayList<>());
	}
	
	public static Class<?> loadClass(final String className, final Iterable<String> packageNames) {
		if(packageNames == null) throw new NullPointerException("packages is null");
		if(className == null) throw new NullPointerException("name is null");
		try {
			return Game.class.getClassLoader().loadClass(className);
		}catch(final ClassNotFoundException e) {
		}
		for(final String packageName : packageNames) try {
			return Game.class.getClassLoader().loadClass(packageName + "." + className);
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
	
	
	public static final void setBuilder(Builder builder) {
		Game.builder = builder;
	}

	private static void reset(final GameScene scene) {
		Events.clear();
		Game.currentScene = scene;
		System.gc();
	}
	
	public static void start(final String file, Builder builder, String[] args) {
		Game.builder = builder;
		
		RootFiles.start(file);
		Properties.loadArguments(args);
		
		while(true) Game.gameLoop();
	}

	public static <S extends GameSystem> boolean addSystem(S system) {
		return getCurrentScene().addSystem(system);
	}
	
}
