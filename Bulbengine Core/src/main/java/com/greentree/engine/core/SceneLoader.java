package com.greentree.engine.core;

import java.io.InputStream;
import java.util.Objects;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.data.loading.ResourceNotFound;
import com.greentree.engine.core.object.GameScene;

/** @author Arseny Latyshev */
public abstract class SceneLoader {

	protected static GameScene currentScene;

	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(SceneLoader.currentScene, "current scene is null");
	}

	public static GameScene getScene(final String file) {
		Log.info("Scene load : " + file);
		try {
			final InputStream inputStream = ResourceLoader.getResourceAsStream(file + ".xml");
			final GameScene   scene       = GameCore.builder.createScene(inputStream);
			GameCore.builder.fillScene(scene, inputStream);
			return scene;
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : %s (the scene file must have \"xml\" extension)", file);
		}
		return null;
	}

	public static GameScene loadScene(final String file) {
		Log.info("Scene load : " + file);
		try {
			final InputStream inputStream = ResourceLoader.getResourceAsStream(file + ".xml");
			final GameScene   scene       = GameCore.builder.createScene(inputStream);
			SceneLoader.reset(scene);
			GameCore.builder.fillScene(scene, inputStream);
			scene.start();
			return scene;
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : %s (the scene file must have \"xml\" extension)", file);
		}
		return null;
	}

	private static void reset(final GameScene scene) {
		Events.clear();
		if(SceneLoader.currentScene != null) SceneLoader.currentScene.destroy();
		SceneLoader.currentScene = null;
		Runtime.getRuntime().gc();
		SceneLoader.currentScene = scene;
	}

}
