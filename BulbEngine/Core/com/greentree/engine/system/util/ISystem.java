package com.greentree.engine.system.util;

import java.io.Serializable;
import java.util.List;

import com.greentree.engine.component.util.GameComponent;

public interface ISystem extends Serializable {

	void execute(List<GameComponent> list);
	//	static ISystem<?> createSystem(final XMLElement element) {
	//		try {
	//			final Class<?> c = Game.loadClass(element.getAttribute("type"));
	//			c.getConstructor().setAccessible(true);
	//			return (ISystem<?>) c.getConstructor().newInstance();
	//		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
	//				| NoSuchMethodException | SecurityException e) {
	//			e.printStackTrace();
	//		}catch(final ClassNotFoundException e) {
	//			Log.error(e.getMessage());
	//		}
	//		return null;
	//	}
}
