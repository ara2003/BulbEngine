package com.greentree.engine.core.system;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.component.NewComponentListener;
import com.greentree.engine.core.util.Events;

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

	public static class MultiBehaviour {

		@SuppressWarnings("unchecked")
		public static <T> void addNewComponentListener(Class<T> clazz, Consumer<T> consumer) {
			Events.addListener((NewComponentListener)c-> {
				if(clazz.isAssignableFrom(c.getClass()))consumer.accept((T) c);
			});
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
