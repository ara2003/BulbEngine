package com.greentree.engine.core;

import com.greentree.common.time.Time;
import com.greentree.engine.core.builder.Builder;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;

public abstract class GameCore {
	
	protected static Builder builder;
	
	public static <S extends GameSystem> boolean addSystem(final S system) {
		return SceneLoader.getCurrentScene().addSystem(system);
	}
	
	public static GameObject createFromPrefab(final String prefab) {
		return GameCore.builder.createPrefab(prefab, SceneLoader.getCurrentScene());
	}
	
	protected final static void gameLoop() {
		Time.updata();
		SceneLoader.getCurrentScene().update();
	}
	
	public static Builder getBuilder() {
		return GameCore.builder;
	}
	
	public static GameScene getCurrentScene() {
		return SceneLoader.getCurrentScene();
	}
	
	@Deprecated
	public static void loadScene(final String name) {
		SceneLoader.loadScene(name);
	}
	
	protected static void setBuilder(final Builder builder) {
		GameCore.builder = builder;
	}
	
	public static void start(final String file, final Builder builder, final String[] args) {
		GameCore.builder = builder;
		RootFiles.start(file);
		Properties.loadArguments(args);
		SceneLoader.loadScene("bootstrap-scene");
		while(true) GameCore.gameLoop();
	}
	
}
