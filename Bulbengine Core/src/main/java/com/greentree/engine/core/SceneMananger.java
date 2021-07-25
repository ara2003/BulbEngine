package com.greentree.engine.core;


import java.util.Objects;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.data.loading.ResourceNotFound;
import com.greentree.engine.core.builder.context.SceneBuildContext;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public abstract class SceneMananger {

	private static final GameScene superParent = new GameScene("super-parent");
	static {
		try {
			superParent.addSystem(new GameSystem<MultiBehaviour>(superParent, (MultiBehaviour) ClassUtil.newInstance(Class.forName("com.greentree.engine.system.TimeSystem"))));
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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
		return loadSceneWithParent(file, superParent);
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
