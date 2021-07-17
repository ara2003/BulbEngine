package com.greentree.engine.core.system;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameScene;

public final class GameSystem {


	private boolean isStart, isDestroy;
	private final MultiBehaviour behaviour;

	private final GameScene scene;

	public GameSystem(GameScene scene, MultiBehaviour behaviour) {
		this.behaviour = behaviour;
		behaviour.system = this;
		this.scene = scene;
	}

	public MultiBehaviour getBehaviour() {
		return behaviour;
	}

	public GameScene getScene() {
		return scene;
	}

	public void initDestroy() {
		if(isDestroy) throw new UnsupportedOperationException("redestroy of : " + this);
		isDestroy = true;
		behaviour.destroy();
	}

	public void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
		behaviour.start();
	}

	public boolean isDestroy() {
		return isDestroy;
	}

	public static class MultiBehaviour {
		private GameSystem system;

		@SuppressWarnings("unchecked")
		public <T> void addNewComponentListener(Class<T> clazz, Consumer<T> consumer) {
			getScene().getNewComponentAction().addListener(c-> {
				if(clazz.isAssignableFrom(c.getClass()))consumer.accept((T) c);
			});
		}

		protected void destroy() {
		}

		public boolean isDestroy() {
			return system.isDestroy;
		}
		
		protected final <T> List<T> getAllComponents(final Class<T> clazz) {
			return system.scene.getAllComponents(clazz);
		}


		protected final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
			return system.scene.getAllComponentsAsComponentList(clazz);
		}

		public final GameScene getScene() {
			return system.scene;
		}

		protected void start() {
		}
		public void update() {
		}
	}

}
