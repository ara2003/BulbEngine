package com.greentree.engine.component;

import com.greentree.engine.Game;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.geom.Shape;

public abstract class ColliderComponent extends GameComponent {

	private static final long serialVersionUID = 1L;
	protected Transform t;

	public abstract Shape getShape();
	public abstract boolean isTriger();
	
	@Override
	protected void start() {
		Game.getCurrentScene().addSystem(new ColliderSystem());
		//		Game.getEventSystem().addListener(new GameObjectListener() {
		//			private static final long serialVersionUID = 1L;
		//
		//			@Override
		//			public void created(GameObject gameObject) {
		//
		//			}
		//
		//		});
	}
}
