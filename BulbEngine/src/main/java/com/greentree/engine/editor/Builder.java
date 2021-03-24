package com.greentree.engine.editor;

import java.io.InputStream;

import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameObjectParent;
import com.greentree.engine.object.GameScene;

/** @author Arseny Latyshev */
public interface Builder {
	
	//create Scene
	GameScene createScene(InputStream in);
	void fillScene(GameScene emptyScene, InputStream in);
	
	//create Object
	GameObject createObject(InputStream in, GameObjectParent parent);
	void fillObject(GameObject emptyObjectBuilder, InputStream in);
	
	//create prefab
	GameObject createPrefab(String prefabName, GameObjectParent parent);
	
}
