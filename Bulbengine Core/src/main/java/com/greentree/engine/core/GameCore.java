package com.greentree.engine.core;

import java.io.File;

import com.greentree.common.concurent.MultyTask;
import com.greentree.common.time.Time;
import com.greentree.engine.core.builder.Builder;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.util.SceneMananger;

public abstract class GameCore {

	protected static Builder builder;

	public static boolean addSystem(final GameSystem<?> system) {
		return SceneMananger.getCurrentSceneNotNull().addSystem(system);
	}

	public static GameObject createFromPrefab(final String prefab) {
		return GameCore.builder.createPrefab(null, prefab);
	}

	public static GameObject createFromPrefab(final String name, final String prefab) {
		return GameCore.builder.createPrefab(name, prefab);
	}

	public static Builder getBuilder() {
		return GameCore.builder;
	}

	@Deprecated
	public static GameScene getCurrentScene() {
		return SceneMananger.getCurrentSceneNotNull();
	}

	protected static void setBuilder(final Builder builder) {
		GameCore.builder = builder;
	}
	
	public static void start(final File root, final Builder builder) {
		GameCore.builder = builder;
	}

	@Deprecated
	public static void exit() {
		System.exit(0);
	}

	@Deprecated
	public static void gameLoop(){
		while(true) {
			Time.updata();
			SceneMananger.getCurrentSceneNotNull().update();
		}
	}

	
	public static void terminate() {
		SceneMananger.terminate();
		MultyTask.shutdown();
	}

}
