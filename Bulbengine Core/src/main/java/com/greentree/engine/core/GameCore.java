package com.greentree.engine.core;

import java.io.File;

import com.greentree.engine.core.builder.Builder;
import com.greentree.engine.core.object.GameObject;

public abstract class GameCore {

	protected static Builder builder;
	protected static boolean running = true;

	public static GameObject createFromPrefab(final String prefab) {
		return createFromPrefab(null, prefab);
	}

	public static GameObject createFromPrefab(final String name, final String prefab) {
		return GameCore.builder.createPrefab(name, prefab);
	}

	public static Builder getBuilder() {
		return GameCore.builder;
	}

	protected static void setBuilder(final Builder builder) {
		GameCore.builder = builder;
	}
	
	public static void start(final File root, final Builder builder) {
		GameCore.builder = builder;
	}

	public static void startGameLoop(){
		while(running) {
			gameLoop();
		}
	}
	
	public final static void exit() {
		running = false;
	}

	@Deprecated
	protected static void gameLoop() {
		SceneMananger.getCurrentSceneNotNull().update();
	}

	
	public static void terminate() {
		SceneMananger.terminate();
	}

}
