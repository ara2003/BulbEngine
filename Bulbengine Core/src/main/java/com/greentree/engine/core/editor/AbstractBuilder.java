package com.greentree.engine.core.editor;

import java.io.InputStream;

import com.greentree.engine.core.GameObject;
import com.greentree.engine.core.GameObjectParent;
import com.greentree.engine.core.GameScene;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {
	
	@Override
	public final GameObject createObject(final InputStream in, final GameObjectParent parent) {
		return this.createObject(this.parse(in), parent);
	}
	
	public final GameObject createObject(final T in, final GameObjectParent parent) {
		return new GameObject(this.getName(in), parent);
	}
	
	@Override
	public final GameScene createScene(final InputStream in) {
		return new GameScene(this.getName(this.parse(in)));
	}
	
	@Override
	public final void fillObject(final GameObject object, final InputStream in) {
		this.fillObject(object, this.parse(in));
	}
	
	protected abstract void fillObject(GameObject node, T in);
	
	@Override
	public final void fillScene(final GameScene scene, final InputStream in) {
		this.fillScene(scene, this.parse(in));
	}
	
	protected abstract void fillScene(GameScene node, T in);
	protected abstract String getName(T in);
	public abstract T parse(InputStream in);
}
