package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;

/** @author Arseny Latyshev */
public interface Builder {
	
	//create Component
	GameComponent createComponent(InputStream in);
	GameComponent createComponent(Class<? extends GameComponent> clazz);
	//create Object
	GameObject createObject(InputStream in, GameObjectParent parent);
	//create prefab
	GameObject createPrefab(String prefabName, GameObjectParent parent);
	//create Scene
	GameScene createScene(InputStream in);
	void fillComponent(GameComponent component, InputStream in);
	void fillObject(GameObject emptyObjectBuilder, InputStream in);
	void fillScene(GameScene emptyScene, InputStream in);
}
