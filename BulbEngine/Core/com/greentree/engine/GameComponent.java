package com.greentree.engine;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Random;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.component.util.GameComponentEvent.EventType;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.event.Listener;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.phisic.ColliderListener;
import com.greentree.serialize.ClassUtil;
import com.greentree.serialize.GsonFileParser;
import com.greentree.xml.XMLElement;

public abstract class GameComponent implements Serializable {
	
	protected static final Random random = new Random();
	private static final long serialVersionUID = 1L;
	private transient boolean initialize = false;
	private GameObject object;
	
	protected GameComponent() {
		Game.event(new GameComponentEvent(EventType.create, this));
	}
	
	@SuppressWarnings("unchecked")
	public final static GameComponent createComponent(final XMLElement data) {
		Class<? extends GameComponent>	clazz = (Class<? extends GameComponent>) Game.loadClass(data.getAttribute("type"));
		GameComponent component = null;
		try {
			component = clazz.getConstructor().newInstance();
		}catch(final Exception e) {
			Log.warn(data, e);
			return null;
		}
		for(final Field f : ClassUtil.getAllFields(component.getClass()))
			if(f.getAnnotation(EditorData.class) != null) try {
				String xmlValue = "";
				{
					String xmlName = f.getAnnotation(EditorData.class).name();
					if(xmlName.equals("*")) xmlName = f.getName();
					if(xmlName.equals("type"))
						Log.error("name of field " + component.getClass() + "." + f.getName() + " is \"type\"");
					xmlValue = data.getAttribute(xmlName);
				}
				if(xmlValue.equals("")) xmlValue = f.getAnnotation(EditorData.class).def();
				if(xmlValue.equals("*")) continue;
				f.setAccessible(true);
				
				if(f.getType().equals(String.class)) f.set(component, xmlValue);
				if(f.getType().equals(int.class)) f.setInt(component, Integer.parseInt(xmlValue));
				if(f.getType().equals(float.class)) f.setFloat(component, Float.parseFloat(xmlValue));
				if(f.getType().equals(boolean.class)) f.setBoolean(component, Boolean.parseBoolean(xmlValue));
				if(f.getType().isEnum()) f.set(component, Enum.valueOf(f.getType().asSubclass(Enum.class), xmlValue));
				
				if(f.get(component) == null) f.set(component, new GsonFileParser().load(xmlValue, f.getType()));
				
				if(f.get(component) == null) {
					Log.warn(component.getClass().getSimpleName() + "." + f.getName() + " is not initialize\n"
							+ Thread.currentThread().getStackTrace()[1]);
					continue;
				}
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return component;
	}
	
	protected void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	public void CollideEvent(final ColliderComponent other) {
	}
	
	public void CollideEvent(final GameObject other) {
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}
	
	public GameObject getObject() {
		return object;
	}
	
	protected void start() {
	}
	
	@SuppressWarnings("unchecked")
	public final void start(final GameObject object) {
		if(initialize) {
			Log.error("second init component " + object + ":" + this);
			return;
		}
		initialize = true;
		this.object = object;
		for(final Field f : ClassUtil.getAllFields(getClass())) try {
			f.setAccessible(true);
			if(f.getType().asSubclass(GameComponent.class) != null)
				if(f.get(this) == null) f.set(this, getComponent((Class<? extends GameComponent>) f.getType()));
		}catch(final Exception e) {
		}
		start();
		Game.addListener(new ColliderListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
				if(object.hasComponent(object1)) {
					CollideEvent(object2);
					CollideEvent(object2.getObject());
				}
				if(object.hasComponent(object2)) {
					CollideEvent(object1);
					CollideEvent(object1.getObject());
				}
			}
		});
	}
	
	protected final void startCorutine(final Corutine corutine) {
		object.startCorutine(corutine);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public void update() {
	}
}
