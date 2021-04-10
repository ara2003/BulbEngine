package com.greentree.engine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;

import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.loading.ResourceNotFound;
import com.greentree.common.logger.Log;
import com.greentree.common.time.Time;
import com.greentree.engine.core.builder.Builder;

public class GameCore {
	
	protected static Builder builder;
	protected static GameScene currentScene;
	
	public static <S extends GameSystem> boolean addSystem(final S system) {
		return GameCore.getCurrentScene().addSystem(system);
	}
	
	public static GameObject createFromPrefab(final String prefab) {
		return GameCore.builder.createPrefab(prefab, GameCore.getCurrentScene());
	}
	
	public static void exit() {
		System.exit(0);
	}
	
	protected static void gameLoop() {
		Time.updata();
		Events.update();
		GameCore.getCurrentScene().update();
	}
	
	public static Builder getBuilder() {
		return GameCore.builder;
	}
	
	
	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(GameCore.currentScene, "current scene is null");
	}
	
	public static void loadScene(final String name) {
		Log.info("Scene load : " + name);
		do {
			try {
				final InputStream inputStream = ResourceLoader.getResourceAsStream(name + ".scene");
				final GameScene   scene       = GameCore.builder.createScene(inputStream);
				GameCore.reset(scene);
				GameCore.builder.fillScene(scene, inputStream);
				scene.start();
			}catch(final ResourceNotFound e) {
				Log.warn("scene not found : %s", name);
				if(Log.question("create scene")) {
					try {
						final File file = new File(RootFiles.getRoot(), name + ".scene");
						try {
							file.createNewFile();
						}catch(final IOException e2) {
							Log.error(e2);
							return;
						}
						try(final PrintStream ps = new PrintStream(file)){
							ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
							ps.println("<scene name=\"scene name\">");
							ps.println("\t");
							ps.println("</scene>");
						}
					}catch(final FileNotFoundException e2) {
						Log.error(e2);
						return;
					}
					continue;
				}
			}
			break;
		}while(true);
	}
	
	private static void reset(final GameScene scene) {
		Events.clear();
		if(currentScene != null)GameCore.currentScene.destroy();
		GameCore.currentScene = null;
		Runtime.getRuntime().gc();
		GameCore.currentScene = scene;
	}
	
	protected static void setBuilder(final Builder builder) {
		GameCore.builder = builder;
	}
	
	public static void start(final String file, final Builder builder, final String[] args) {
		GameCore.builder = builder;
		
		RootFiles.start(file);
		Properties.loadArguments(args);
		
		while(true) GameCore.gameLoop();
	}
	
}
