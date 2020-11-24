package com.greentree.engine.component.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.Time;
import com.greentree.engine.loading.LoaderUtil;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.Scene;
import com.greentree.util.xml.XMLElement;

public abstract class GameComponent implements Serializable {

	protected static final Random random = new Random();
	private static final long serialVersionUID = 1L;
	transient boolean initialize = false;
	protected transient GameObject object;
	protected transient Scene scene;
	
	protected GameComponent() {
	}
	
	public void CollideEvent(final GameObject other) {
	}
	
	public void destroy() {
		object.destroy();
	}

	protected final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return object.getComponent(clazz);
	}
	
	protected long getTime() {
		return System.currentTimeMillis();
	}

	protected void start() {
	}

	@SuppressWarnings("unchecked")
	public final void start(final Scene scene, final GameObject object) {
		if(initialize) {
			Log.error("second init component " + object + ":" + this + " from "
					+ Thread.currentThread().getStackTrace()[2]);
			return;
		}
		initialize = true;
		this.scene = scene;
		this.object = object;
		for(final Field f : LoaderUtil.getAllFields(getClass())) try {
			f.setAccessible(true);
			if(f.getType().asSubclass(GameComponent.class) != null)
				if(f.get(this) == null) f.set(this, getComponent((Class<? extends GameComponent>) f.getType()));
		}catch(IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}catch(final ClassCastException e) {
		}
		start();
	}

	protected void startCorutine(final Corutine cor) {
		new Thread(()-> {
			try {
				cor.run();
			}catch(final InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public void update() {
	}
	
	@SuppressWarnings("unchecked")
	public static GameComponent createComponent(final XMLElement in) {
		final String[] w = in.getAttribute("type").split(":");
		Class<?> clazz;
		try {
			clazz = Game.loadClass(w[w.length - 1]);
		}catch(final ClassNotFoundException e) {
			Log.warn("component type " + in.getAttribute("type") + " not found");
			return null;
		}
		GameComponent component = null;
		try {
			component = (GameComponent) clazz.getConstructor().newInstance();
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if(component == null) return null;
		for(final Field f : LoaderUtil.getAllFields(component.getClass()))
			if(f.getAnnotation(XmlData.class) != null) try {
				String xmlValue = "";
				{
					String xmlName = f.getAnnotation(XmlData.class).name(); // ��� ���� � xml �����
					if(xmlName.equals("*")) xmlName = f.getName(); // ���� ��� �� ������ ���������, �� ����� ��� ����
					if(xmlName.equals("type"))
						Log.error("name of field " + component.getClass() + "." + f.getName() + " is \"type\"");
					xmlValue = in.getAttribute(xmlName);
				}
				if(xmlValue.equals("")) xmlValue = f.getAnnotation(XmlData.class).def();
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

	@FunctionalInterface
	protected interface Corutine {

		void run() throws InterruptedException;
	}
}
