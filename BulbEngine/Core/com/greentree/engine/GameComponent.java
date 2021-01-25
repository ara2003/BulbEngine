package com.greentree.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.event.Listener;
import com.greentree.engine.phisic.ColliderListener;

public abstract class GameComponent extends Constants implements Serializable {
	
	protected static final Random random = new Random();
	private static final long serialVersionUID = 1L;
	private transient boolean awake, start;
	private GameNode node;
	private transient final List<Corutine> corutines;
	
	protected GameComponent() {
		corutines = new ArrayList<>(0);
	}

	
	protected void addComponentListener(ColliderListenerFun collider) {
		Game.addListener(new ColliderListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
				if(node.hasComponent(object1)) {
					collider.event(object2.getObject());
				}
				if(node.hasComponent(object2)) {
					collider.event(object1.getObject());
				}
			}
		});
	}
	
	protected void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected void awake() {
	}
	
	final void awake(final GameNode object) {
		if(awake) {
			Log.error("second awake component " + object + ":" + this);
			return;
		}
		awake = true;
		this.node = object;
		awake();
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}
	
	public GameNode getObject() {
		return this.node;
	}
	
	protected void start() {
	}
	
	final void start(final GameNode node) {
		if(start) {
			Log.error("second start component " + this.node + ":" + this);
			return;
		}
		start = true;
		this.node = node;
		start();
	}
	
	protected final void startCorutine(final Corutine corutine) {
		corutines.add(corutine);
	}
	
	@Override
	public final String toString() {
		return "GameComponent[" + getClass().getSimpleName() + "]@" + hashCode();
	}

	protected void update() {
	}
	
	public void update0() {
		update();
		for(Corutine corutine : corutines)if(corutine.run())corutines.remove(corutine);
	}
	
	public interface ColliderListenerFun {
		
		void event(GameNode object);
		
	}
}
