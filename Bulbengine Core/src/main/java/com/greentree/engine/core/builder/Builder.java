package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.builder.context.SceneBuildContext;
import com.greentree.engine.core.object.GameObject;

/** @author Arseny Latyshev */
public interface Builder {

	//create prefab
	GameObject createPrefab(String name, String prefabName);

	
	SceneBuildContext createScene(InputStream in);
	//create Scene
//	GameScene createScene(InputStream in);
//	void fillScene(GameScene emptyScene, InputStream in);

}

