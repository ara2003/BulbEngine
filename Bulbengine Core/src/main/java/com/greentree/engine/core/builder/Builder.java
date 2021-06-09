package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.layer.Layer;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
public interface Builder {

	GameComponent createComponent(Class<? extends GameComponent> clazz);
	//create Component
	GameComponent createComponent(InputStream in);
	//create Object
	GameObject createObject(InputStream in, GameObjectParent parent);
	//create prefab
	GameObject createPrefab(String prefabName, GameObjectParent parent);
	//create Scene
	GameScene createScene(InputStream in);
	//create System
	GameSystem createSystem(Class<? extends GameSystem> cl);
	void fillComponent(GameComponent component, InputStream in);
	void fillObject(GameObject emptyObjectBuilder, InputStream in);
	void fillScene(GameScene emptyScene, InputStream in);
	void fillSystem(GameSystem system, InputStream in);
	//create System
	Layer getLayer(String name);

}

