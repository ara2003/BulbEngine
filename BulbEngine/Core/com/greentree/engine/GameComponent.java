package com.greentree.engine;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Random;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.component.util.GameComponentEvent.EventType;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.event.Listener;
import com.greentree.engine.phisic.ColliderListener;
import com.greentree.serialize.ClassUtil;

public abstract class GameComponent implements Serializable {
	
	protected static final Random random = new Random();
	private static final long serialVersionUID = 1L;
	private transient boolean awake, start;
	private GameObject object;
	
	protected GameComponent() {
		Game.event(new GameComponentEvent(EventType.create, this));
	}
	
	protected void addComponentListener(ColliderListenerFun collider) {
		Game.addListener(new ColliderListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
				if(object.hasComponent(object1)) {
					collider.event(object2.getObject());
				}
				if(object.hasComponent(object2)) {
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
	
	@SuppressWarnings("unchecked")
	final void awake(final GameObject object) {
		if(awake) {
			Log.error("second awake component " + object + ":" + this);
			return;
		}
		awake = true;
		this.object = object;
		for(final Field f : ClassUtil.getAllFields(getClass())) try {
			f.setAccessible(true);
			if(f.getType().asSubclass(GameComponent.class) != null)
				if(f.get(this) == null) f.set(this, getComponent((Class<? extends GameComponent>) f.getType()));
		}catch(final Exception e) {
		}
		awake();
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}
	
	public GameObject getObject() {
		return object;
	}
	
	protected void start() {
	}
	
	final void start(final GameObject object) {
		if(start) {
			Log.error("second start component " + object + ":" + this);
			return;
		}
		start = true;
		start();
	}
	
	protected final void startCorutine(final Corutine corutine) {
		object.startCorutine(corutine);
	}
	
	@Override
	public String toString() {
		return object+"."+getClass().getSimpleName();
	}
	
	public void update() {
	}
	
	public interface ColliderListenerFun {
		
		void event(GameObject object);
		
	}
}
