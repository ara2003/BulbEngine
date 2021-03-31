package com.greentree.engine.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

import com.greentree.common.Log;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.event.Listener;

public abstract class GameSystem extends GameElement {
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameObject createFromPrefab(final String prefab) {
		return Game.createFromPrefab(prefab);
	}
	
	public static GameSystem createSystem(final Class<?> clazz) {
		if(clazz == null) throw new NullPointerException("clazz is null");
		try {
			final Constructor<?> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			final GameSystem gs = (GameSystem) constructor.newInstance();
			return gs;
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Log.error(e);
		}
		return null;
	}
	
	protected <T extends GameComponent> Queue<T> getAllComponents(final Class<T> clazz) {
		return Game.getCurrentScene().getAllComponents(clazz);
	}
	
	protected <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return Game.getCurrentScene().getAllComponentsAsComponentList(clazz);
	}
}
