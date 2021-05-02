package com.greentree.engine.core;

import java.util.ArrayList;
import java.util.List;

import com.greentree.common.time.Time;
import com.greentree.engine.core.builder.Builder;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;

public abstract class GameCore {
	
	protected static Builder builder;
	private static final List<Runnable> tasks = new ArrayList<>();
	
	public static <S extends GameSystem> boolean addSystem(final S system) {
		return SceneLoader.getCurrentScene().addSystem(system);
	}
	
	public static GameObject createFromPrefab(final String prefab) {
		return GameCore.builder.createPrefab(prefab, SceneLoader.getCurrentScene());
	}
	
	protected final static void gameLoop() {
		Time.updata();
		Events.update();
		for(Runnable runnable : tasks)runnable.run();
		tasks.clear();
		SceneLoader.getCurrentScene().update();
	}
	
	public static GameScene getCurrentScene() {
		return SceneLoader.getCurrentScene();
	}
	
	public static Builder getBuilder() {
		return GameCore.builder;
	}

	@Deprecated
	public static void loadScene(final String name) {
		SceneLoader.loadScene(name);
	}

	@Deprecated
	protected static void loadScene0(final String name) {
		SceneLoader.loadScene0(name);
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

	public static void task(Runnable runnable) {
		tasks.add(runnable);
	}
	
}
