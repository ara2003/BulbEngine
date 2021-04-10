package com.greentree.engine.core.builder;

import java.io.InputStream;

import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.GameObject;
import com.greentree.engine.core.GameObjectParent;
import com.greentree.engine.core.GameScene;

/** @author Arseny Latyshev */
public interface Builder {
	
	GameComponent createComponent(Class<? extends GameComponent> class1);
	//create Component
	GameComponent createComponent(InputStream in);
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
