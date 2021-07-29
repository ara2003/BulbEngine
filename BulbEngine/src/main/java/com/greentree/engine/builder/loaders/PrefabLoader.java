package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.Game;
import com.greentree.engine.core.node.GameObject;

public class PrefabLoader extends AbstractLoader implements ValueLoader {

	public PrefabLoader() {
		super(GameObject.class);
	}

	@Override
	public Object parse(Class<?> clazz, String value) throws Exception {
		return Game.getBuilder().createPrefab(value, value);
	}

}
