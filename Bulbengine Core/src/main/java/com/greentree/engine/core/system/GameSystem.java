package com.greentree.engine.core.system;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.component.NewComponentListener;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.util.Events;
import com.greentree.event.Listener;

public final class GameSystem  {
	private boolean isStart = false;

	private final MultiBehaviour behaviour;

	public GameSystem(MultiBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public MultiBehaviour getBehaviour() {
		return behaviour;
	}
	public void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
	    behaviour.start();
	}
	
	public static class MultiBehaviour{

		@Deprecated
		protected final static void addListener(final Listener listener) {
			Events.addListener(listener);
		}

		@SuppressWarnings("unchecked")
		public static <T> void addNewComponentListener(Class<T> clazz, Consumer<T> consumer) {
			addListener((NewComponentListener)c-> {
				if(clazz.isAssignableFrom(c.getClass()))consumer.accept((T) c);
			});
		}

		@Deprecated
		protected final static GameObject createFromPrefab(final String prefab) {
			return createFromPrefab(prefab, prefab);
		}

		@Deprecated
		protected final static GameObject createFromPrefab(final String name, final String prefab) {
			return GameCore.createFromPrefab(name, prefab);
		}
		protected final <T> List<T> getAllComponents(final Class<T> clazz) {
			return GameCore.getCurrentScene().getAllComponents(clazz);
		}

		protected final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
			return GameCore.getCurrentScene().getAllComponentsAsComponentList(clazz);
		}

		protected void start() {
		}

		public void update() {
		}
	}
}
