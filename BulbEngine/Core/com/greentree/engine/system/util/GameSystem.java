package com.greentree.engine.system.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Set;

import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.event.Listener;
import com.greentree.engine.input.KeyAdapter;
import com.greentree.engine.object.GameComponent;

public abstract class GameSystem implements Serializable {

	private static final long serialVersionUID = 1L;
	protected static final Random random = new Random();

	protected void addListener(Listener listener) {
		Game.getEventSystem().addListener(listener);
	}

	
	public static GameSystem createSystem(final Class<?> c) {
		try {
			final Constructor<?> constructor = c.getConstructor();
			constructor.setAccessible(true);
			final GameSystem gs = (GameSystem) constructor.newInstance();
			return gs;
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Log.error(e);
		}
		return null;
	}
	//	private void add(final GameComponent component) {
	//		if(isUsedClass(component.getClass())) {
	//			Set<GameComponent> c = components.get(component.getClass());
	//			if(c == null) {
	//				c = new HashSet<>();
	//				components.put(component.getClass(), c);
	//			}
	//			c.add(component);
	//		}
	//	}

	public abstract void execute();

	protected <T extends GameComponent> Set<T> getComponents(final Class<T> clazz) {
		return Game.getCurrentScene().getComponents(clazz);
	}
	//	@SuppressWarnings("unchecked")
	//	protected <T extends GameComponent> Set<T> getComponentsAssignableFrom(final Class<T> clazz) {
	//		final Set<T> set = new HashSet<>();
	//		for(final Class<? extends GameComponent> c : components.keySet())
	//			if(clazz.isAssignableFrom(c)) set.addAll((Collection<? extends T>) components.get(c));
	//		return set;
	//	}

	public final void init() {
		start();
		//		Game.getEventSystem().addListener(new GameComponentListener() {
		//			private static final long serialVersionUID = 1L;
		//
		//			@Override
		//			public void create(final GameComponent component) {
		//				add(component);
		//			}
		//
		//			@Override
		//			public void destroy(final GameComponent component) {
		//				remove(component);
		//			}
		//		});
	}
	//
	//	private boolean isUsedClass(final Class<?> clazz) {
	//		for(final Class<?> c : LoaderUtil.getAllPerant(clazz)) if(components.containsKey(c)) return true;
	//		return false;
	//	}
	//	private void remove(final GameComponent component) {
	//		if(isUsedClass(component.getClass())) components.get(component.getClass()).remove(component);
	//	}
	
	protected void start() {
	}
}
