package com.greentree.engine.object;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.component.util.necessarilySystem;
import com.greentree.engine.component.util.GameComponentEvent.EventType;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.loading.LoaderUtil;
import com.greentree.engine.phisic.ColliderListener;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.util.xml.XMLElement;

public abstract class GameComponent implements Serializable {

	protected static final Random random = new Random();
	private static final long serialVersionUID = 1L;
	private transient boolean initialize = false;
	private GameObject object;

	protected GameComponent() {
		Game.getEventSystem().event(new GameComponentEvent(EventType.create, this));
	}
	
	@SuppressWarnings("unchecked")
	public final static GameComponent createComponent(final XMLElement in) {
		final String[] w = in.getAttribute("type").split(":");
		Class<?> clazz;
		clazz = Game.loadClass(w[w.length - 1]);
		GameComponent component = null;
		try {
			component = (GameComponent) clazz.getConstructor().newInstance();
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if(component == null) return null;
		for(final Field f : LoaderUtil.getAllFields(component.getClass()))
			if(f.getAnnotation(EditorData.class) != null) try {
				String xmlValue = "";
				{
					String xmlName = f.getAnnotation(EditorData.class).name();
					if(xmlName.equals("*")) xmlName = f.getName();
					if(xmlName.equals("type"))
						Log.error("name of field " + component.getClass() + "." + f.getName() + " is \"type\"");
					xmlValue = in.getAttribute(xmlName);
				}
				if(xmlValue.equals("")) xmlValue = f.getAnnotation(EditorData.class).def();
				if(xmlValue.equals("*")) continue;
				f.setAccessible(true);
				if(f.getType().equals(String.class)) f.set(component, xmlValue);
				if(f.getType().equals(int.class)) f.setInt(component, Integer.parseInt(xmlValue));
				if(f.getType().equals(float.class)) f.setFloat(component, Float.parseFloat(xmlValue));
				if(f.getType().equals(boolean.class)) f.setBoolean(component, Boolean.parseBoolean(xmlValue));
				if(f.getType().isEnum()) f.set(component, Enum.valueOf(f.getType().asSubclass(Enum.class), xmlValue));
				/*
				if(f.get(component) == null) try {
					if(Arrays.asList(f.getType().getInterfaces()).contains(gsonSerializable.class)) try {
						f.set(component,
								ClassParser.parse(new File(Game.getRoot(),
										xmlValue + "."
												+ (String) f.getType()
														.getMethod(gsonSerializable.class.getMethods()[0].getName())
														.invoke(f.get(component))),
										f.getType()));
					}catch(InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					if(f.get(component) == null) try {
						try {
							f.set(component, Game.loadClass(xmlValue).getConstructor().newInstance());
						}catch(final ClassNotFoundException e) {
						}
					}catch(InstantiationException | InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
								e.printStackTrace();
							}
				}catch(final JsonSyntaxException e) {
					Log.warn(f.getType() + " is annotated " + XmlData.class.getSimpleName() + " and file "
							+ new File(Game.getRoot(), xmlValue) + " don't json");
				}
				*/
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
	
	public void CollideEvent(final GameObject other) {
	}
	
	protected final <T extends GameComponent> T getComponent(final Class<T> clazz) {
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
			Log.error("second init component " + object + ":" + this + " from "
					+ Thread.currentThread().getStackTrace()[2]);
			return;
		}
		initialize = true;
		this.object = object;
		{
			final necessarilySystem necessarilySystem = LoaderUtil.getAnnotationofPeraent(getClass(),
					necessarilySystem.class);
			if(necessarilySystem != null) for(final Class<? extends GameSystem> c : necessarilySystem.value())
				Game.getCurrentScene().addSystem(c);
		}
		for(final Field f : LoaderUtil.getAllFields(getClass())) try {
			f.setAccessible(true);
			if(f.getType().asSubclass(GameComponent.class) != null)
				if(f.get(this) == null) f.set(this, getComponent((Class<? extends GameComponent>) f.getType()));
		}catch(IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}catch(final ClassCastException e) {
		}
		start();
		Game.getEventSystem().addListener(new ColliderListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void CollisionStay(final GameObject object1, final GameObject object2) {
				if(object == object1) CollideEvent(object2);
				if(object == object2) CollideEvent(object1);
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
