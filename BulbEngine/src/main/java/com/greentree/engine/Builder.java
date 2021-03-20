package com.greentree.engine;

import java.io.InputStream;

import com.greentree.engine.object.GameNode;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameScene;

/** @author Arseny Latyshev */
public interface Builder {
	
	GameObject.Builder createObject(InputStream in, GameNode parent);
	GameObject createObject(String prefab, GameNode parent);
	GameScene.Builder createSceneBuilder(InputStream in);
	void fillScene(GameScene.Builder emptyScene, InputStream in);
	void fillObject(GameObject.Builder emptyObjectBuilder, InputStream in);
}
