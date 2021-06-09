package com.greentree.engine.core.object;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.component.NewComponent;
import com.greentree.event.Listener;

public abstract class GameSystem extends StartGameElement {

	protected final static void addListener(final Listener listener) {
		Events.addListener(listener);
	}

	public static <T> void addNewComponentListener(Class<T> clazz, Consumer<T> consumer) {
		addListener(new NewComponent() {

			@SuppressWarnings("unchecked")
			@Override
			public void newComponent(GameComponent c) {
				if(clazz.isAssignableFrom(c.getClass()))consumer.accept((T) c);
			}

		});
	}

	protected final static GameObject createFromPrefab(final String prefab) {
		return GameCore.createFromPrefab(prefab);
	}

	protected final <T> List<T> getAllComponents(final Class<T> clazz) {
		return GameCore.getCurrentScene().getAllComponents(clazz);
	}

	protected final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return GameCore.getCurrentScene().getAllComponentsAsComponentList(clazz);
	}

	public void update() {
	}
}
