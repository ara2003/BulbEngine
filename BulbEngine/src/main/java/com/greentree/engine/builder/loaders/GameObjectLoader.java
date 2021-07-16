package com.greentree.engine.builder.loaders;

import java.util.List;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.util.SceneMananger;

/** @author Arseny Latyshev */
public class GameObjectLoader extends AbstractLoader implements ValueLoader {

	public GameObjectLoader() {
		super(GameObject.class);
	}

	@Override
	public Object parse(final Class<?> clazz, final String value) throws Exception {
		final List<GameObject> list = SceneMananger.getCurrentSceneNotNull().findObjectsWithName(value);
		if(!list.isEmpty()) return list.get(0);
		throw new IllegalArgumentException();
	}
}
