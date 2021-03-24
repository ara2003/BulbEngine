package com.greentree.engine.editor;

import java.io.InputStream;

import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameObjectParent;
import com.greentree.engine.object.GameScene;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {

	
	@Override
	public final GameObject createObject(InputStream in, GameObjectParent parent) {
		return createObject(parse(in), parent);
	}
	
	public final GameObject createObject(T in, GameObjectParent parent) {
		return new GameObject(getName(in), parent);
	}
	
	@Override
	public final GameScene createScene(InputStream in) {
		return new GameScene(getName(parse(in)));
	}

	@Override
	public final void fillObject(GameObject object, InputStream in) {
		fillObject(object, parse(in));
	}
	
	@Override
	public final void fillScene(GameScene scene, InputStream in) {
		fillScene(scene, parse(in));
	}

	protected abstract void fillObject(GameObject node, T in);
	protected abstract void fillScene(GameScene node, T in);
	protected abstract String getName(T in);
	public abstract T parse(InputStream in);
	
}
