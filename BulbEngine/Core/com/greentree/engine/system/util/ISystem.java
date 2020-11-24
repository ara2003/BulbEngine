package com.greentree.engine.system.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.util.xml.XMLElement;

public abstract class ISystem<component extends GameComponent> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract void execute(List<component> gs);

	static ISystem<?> createSystem(final XMLElement element) {
		try {
			final Class<?> c = Game.loadClass(element.getAttribute("type"));
			c.getConstructor().setAccessible(true);
			return (ISystem<?>) c.getConstructor().newInstance();
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}catch(final ClassNotFoundException e) {
			Log.error(e.getMessage());
		}
		return null;
	}
}
