package com.greentree.engine.core.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.data.loading.ResourceNotFound;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameScene;

/** @author Arseny Latyshev */
public abstract class SceneMananger {

	protected static GameScene currentScene;

	public static GameScene getCurrentScene() {
		return Objects.requireNonNull(SceneMananger.currentScene, "current scene is null");
	}

	public static GameScene getScene(String file) {
		Log.info("Scene load : " + file);
		try {
			if(!file.endsWith(".xml"))file += ".xml";
			final InputStream inputStream = ResourceLoader.getResourceAsStream(file);
			final GameScene   scene       = GameCore.getBuilder().createScene(inputStream);
			GameCore.getBuilder().fillScene(scene, inputStream);
			return scene;
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : %s", file);
		}
		return null;
	}

	public static GameScene loadScene(final File file) throws FileNotFoundException {
		return loadScene0(new FileInputStream(file));
	}

	public static GameScene loadScene(String file) {
		Log.info("Scene load : " + file);
		try {
			if(!file.endsWith(".xml"))file += ".xml";
			return loadScene0(ResourceLoader.getResourceAsStream(file));
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : %s", file);
			e.printStackTrace();
		}
		return null;
	}
	private static GameScene loadScene0(final InputStream inputStream) {
		final GameScene   scene       = GameCore.getBuilder().createScene(inputStream);
		SceneMananger.reset(scene);
		GameCore.getBuilder().fillScene(scene, inputStream);
		scene.start();
		return scene;
	}

	private static void reset(final GameScene scene) {
		Events.clear();
		if(SceneMananger.currentScene != null) SceneMananger.currentScene.destroy();
		SceneMananger.currentScene = null;
		Runtime.getRuntime().gc();
		SceneMananger.currentScene = scene;
	}

	public static boolean isCurrent(GameScene scene) {
		return scene == currentScene;
	}

}
