package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameComponent;

/** @author Arseny Latyshev */
public class GameComponentLoader extends AbstractLoader implements ValueLoader {

	public GameComponentLoader() {
		super(GameComponent.class);
	}

	@Override
	public Object parse(final Class<?> clazz, final String name) throws Exception {
		for(var c : GameCore.getCurrentScene().findObjectsWithName(name)) {
			var res = c.getComponent(clazz.asSubclass(GameComponent.class));
			if(res == null)continue;
			return res;
		}
		throw new Exception("component "+clazz+" not find in objects with name " + name + " : " + GameCore.getCurrentScene().findObjectsWithName(name));
	}
}
