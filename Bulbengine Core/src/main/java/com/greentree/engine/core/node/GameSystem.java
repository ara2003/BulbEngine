package com.greentree.engine.core.node;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;

public final class GameSystem<B extends MultiBehaviour> {

	private boolean isStart, isDestroy;
	private final B behaviour;

	private final GameScene scene;

	public GameSystem(GameScene scene, B behaviour) {
		this.behaviour = behaviour;
		behaviour.system = this;
		this.scene = scene;
	}

	public B getBehaviour() {
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
		GameSystem<?> system;

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

		public final GameScene getScene() {
			return system.scene;
		}

		protected void start() {
		}
		public void update() {
		}
	}

}
