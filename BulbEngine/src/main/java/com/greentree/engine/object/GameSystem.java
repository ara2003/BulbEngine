package com.greentree.engine.object;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Set;

import com.greentree.common.Log;
import com.greentree.engine.Game;
import com.greentree.engine.component.ComponentList;

public abstract class GameSystem extends GameElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected static final Random random = new Random();
	
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
	
	public void execute() {
	}

	protected <T extends GameComponent> Set<T> getAllComponents(final Class<T> clazz) {
		return Game.getCurrentScene().getAllComponents(clazz);
	}
	protected <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return Game.getCurrentScene().getAllComponentsAsComponentList(clazz);
	}
	
	public final void init() {
		start();
	}
	
	protected void start() {
	}
}
