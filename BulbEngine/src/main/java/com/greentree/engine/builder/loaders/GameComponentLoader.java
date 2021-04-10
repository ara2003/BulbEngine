package com.greentree.engine.builder.loaders;

import java.util.List;

import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.GameObject;

/** @author Arseny Latyshev */
public class GameComponentLoader implements Loader {
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		return GameComponent.class.isAssignableFrom(clazz);
	}
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) {
		final List<GameObject> list = GameCore.getCurrentScene().findObjectsWithName(value);
		if(!list.isEmpty())
			return list.get(0).getComponent(fieldClass.asSubclass(GameComponent.class));
		return null;
	}
	
	@Override
	public Object load(final String value) throws Exception {
		throw new UnsupportedOperationException("use load(Class clazz, String value)");
	}
}
