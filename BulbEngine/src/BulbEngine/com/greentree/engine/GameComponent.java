package com.greentree.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.phisic.ColliderListener;
import com.greentree.util.Log;

public abstract class GameComponent extends Constants implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient boolean awake, start;
	private GameObject node;
	private transient final List<Corutine> corutines;
	
	protected GameComponent() {
		corutines = new ArrayList<>(0);
	}
	
	protected void addColliderListener(ColliderListenerFun collider) {
		Game.addListener(new ColliderListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
				if(node.hasComponent(object1)) {
					collider.event(object2.getNode());
				}
				if(node.hasComponent(object2)) {
					collider.event(object1.getNode());
				}
			}
		});
	}
	
	protected void awake() {
	}
	
	final void awake(final GameObject object) {
		if(awake) {
			Log.error("second awake component " + object + ":" + this);
			return;
		}
		awake = true;
		node = object;
		awake();
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getNode().getComponent(clazz);
	}
	
	public GameObject getNode() {
		return node;
	}
	
	protected void start() {
	}
	
	final void start(final GameObject node) {
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
		for(Corutine corutine : corutines) if(corutine.run()) corutines.remove(corutine);
	}
	
	public interface ColliderListenerFun {
		
		void event(GameObject object);
	}
}
