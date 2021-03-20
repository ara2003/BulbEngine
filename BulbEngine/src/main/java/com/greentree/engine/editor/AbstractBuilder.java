package com.greentree.engine.editor;

import java.io.InputStream;

import com.greentree.engine.Builder;
import com.greentree.engine.object.GameNode;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameScene;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {
	
	@Override
	public final GameObject.Builder createObject(InputStream in, GameNode parent) {
		return GameObject.builder(getName(parse(in)), parent);
	}
	
	@Override
	public final GameScene.Builder createSceneBuilder(InputStream in) {
		return GameScene.builder(getName(parse(in)));
	}

	@Override
	public final void fillObject(GameObject.Builder object, InputStream in) {
		fillObject(object, parse(in));
	}
	
	@Override
	public final void fillScene(GameScene.Builder scene, InputStream in) {
		fillScene(scene, parse(in));
	}

	protected abstract void fillObject(GameObject.Builder node, T in);
	protected abstract void fillScene(GameScene.Builder node, T in);
	protected abstract String getName(T in);
	public abstract T parse(InputStream in);

	@Override
	public abstract GameObject createObject(String prefab, GameNode parent);
}
