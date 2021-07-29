package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.builder.context.SceneBuildContext;
import com.greentree.engine.core.node.GameObject;
import com.greentree.engine.core.node.GameScene;

/** @author Arseny Latyshev */
public interface Builder {

	//create prefab
	GameObject createPrefab(String name, String prefabName);


	//create Scene
	SceneBuildContext createScene(InputStream in, GameScene parent);

}

