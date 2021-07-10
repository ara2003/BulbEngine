package com.greentree.engine.core.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.data.loading.ResourceNotFound;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.builder.context.SceneBuildContext;
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
			final SceneBuildContext   sceneBuildContext = GameCore.getBuilder().createScene(inputStream);

			final GameScene scene = sceneBuildContext.fill();

			return scene;
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : " + file);
		}
		return null;
	}

	public static boolean isCurrent(GameScene scene) {
		return scene == currentScene;
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
			Log.warn("scene not found : " + file);
			e.printStackTrace();
		}
		return null;
	}

	private static GameScene loadScene0(final InputStream inputStream) {
		final SceneBuildContext   sceneBuildContext       = GameCore.getBuilder().createScene(inputStream);
		GameScene scene = sceneBuildContext.getScene();
		SceneMananger.reset(scene);
		sceneBuildContext.fill();
		scene.initSratr();
		return scene;
	}

	private static void reset(final GameScene scene) {
		Events.clear();
		if(SceneMananger.currentScene != null) SceneMananger.currentScene.destroy();
		SceneMananger.currentScene = null;
		Runtime.getRuntime().gc();
		SceneMananger.currentScene = scene;
	}

	public static void terminate() {
		currentScene.destroy();
		currentScene = null;
	}

}
