package com.greentree.engine.core;

import java.io.InputStream;
import java.util.Objects;

import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.loading.ResourceNotFound;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.object.GameScene;

/** @author Arseny Latyshev */
public abstract class SceneLoader {
	
	protected static GameScene currentScene;
	
	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(SceneLoader.currentScene, "current scene is null");
	}
	
	public static void loadScene(final String name) {
		Log.info("Scene load : " + name);
		try {
			final InputStream inputStream = ResourceLoader.getResourceAsStream(name + ".xml");
			final GameScene   scene       = GameCore.builder.createScene(inputStream);
			SceneLoader.reset(scene);
			GameCore.builder.fillScene(scene, inputStream);
			scene.start();
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : %s (the scene file must have \"xml\" extension)", name);
		}
	}
	
	private static void reset(final GameScene scene) {
		Events.clear();
		if(SceneLoader.currentScene != null) SceneLoader.currentScene.destroy();
		SceneLoader.currentScene = null;
		Runtime.getRuntime().gc();
		SceneLoader.currentScene = scene;
	}
	
}
