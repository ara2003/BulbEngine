package com.greentree.engine.builder.loaders;

import java.util.List;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.object.GameObject;

/** @author Arseny Latyshev */
public class GameComponentLoader extends AbstractLoader<GameComponent> implements ValueLoader {

	public GameComponentLoader() {
		super(GameComponent.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(final Class<T> clazz, final String value) throws Exception {

		final List<GameObject> list = GameCore.getCurrentScene().findObjectsWithName(value);
		if(!list.isEmpty()) return (T) list.get(0).getComponent(clazz.asSubclass(GameComponent.class));
		return null;
	}
}
