package com.greentree.engine.system;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.greentree.engine.Constants;
import com.greentree.engine.Game;
import com.greentree.engine.GameComponent;
import com.greentree.engine.Log;
import com.greentree.engine.component.ComponentList;

public abstract class GameSystem extends Constants implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected static final Random random = new Random();
	
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
	
	public void execute() {
	}
	
	protected <T extends GameComponent> ComponentList<T> getComponents(final Class<T> clazz) {
		return Game.getMainNode().getAllComponents(clazz);
	}
	
	public final void init() {
		start();
	}
	
	protected void start() {
	}
}
