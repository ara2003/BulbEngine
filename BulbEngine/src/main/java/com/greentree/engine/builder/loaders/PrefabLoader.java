package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.Game3D;
import com.greentree.engine.core.object.GameObject;

public class PrefabLoader extends AbstractLoader implements ValueLoader {

	public PrefabLoader() {
		super(GameObject.class);
	}

	@Override
	public Object parse(Class<?> clazz, String value) throws Exception {
		return Game3D.getBuilder().createPrefab(value, value, null);
	}

}
