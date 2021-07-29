package com.greentree.engine.core;


import java.util.Objects;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.data.loading.ResourceNotFound;
import com.greentree.engine.core.builder.context.SceneBuildContext;
import com.greentree.engine.core.node.GameScene;

/** @author Arseny Latyshev */
public abstract class SceneMananger {
	
	protected static GameScene currentScene;

	public static GameScene getCurrentScene() {
		return SceneMananger.currentScene;
	}

	public static GameScene getCurrentSceneNotNull() {
		return Objects.requireNonNull(getCurrentScene(), "current scene is null");
	}

	public static boolean isCurrent(GameScene scene) {
		return scene == currentScene;
	}
	
	public static GameScene loadScene(String file) {
		return loadSceneWithParent(file, null);
	}
	
	public static GameScene loadSceneCurretAsParent(String file) {
		return loadSceneWithParent(file, currentScene);
	}
	
	public static GameScene loadSceneWithParent(String file, GameScene parent) {
		Log.info("Scene load : " + file);
		try {
			if(!file.endsWith(".xml"))file += ".xml";
    		final SceneBuildContext   sceneBuildContext       = GameCore.getBuilder().createScene(ResourceLoader.getResourceAsStream(file), parent);
    		GameScene scene = sceneBuildContext.getScene();
    
    		SceneMananger.reset(scene);//TODO
    
    		sceneBuildContext.fill();
			scene.initSratr();
			return scene;
		}catch(final ResourceNotFound e) {
			Log.warn("scene not found : " + file);
			e.printStackTrace();
		}
		return null;
	}

	private static void reset(final GameScene scene) {
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
