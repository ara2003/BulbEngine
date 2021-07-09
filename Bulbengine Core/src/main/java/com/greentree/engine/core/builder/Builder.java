package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameScene;

/** @author Arseny Latyshev */
public interface Builder {

	//create prefab
	GameObject createPrefab(String name, String prefabName);

	//create Scene
	GameScene createScene(InputStream in);
	void fillScene(GameScene emptyScene, InputStream in);

}

