package com.greentree.engine.editor.xml.loaders;

import java.util.List;

import com.greentree.engine.Game;
import com.greentree.engine.editor.xml.Loader;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
public class GameComponentLoader implements Loader {

	@Override
	public GameComponent load(Class<?> fieldClass, String value) {
		if(GameComponent.class.isAssignableFrom(fieldClass)) {
			final List<GameObject> list = Game.getCurrentScene().findObjectsWithName(value);
			if(!list.isEmpty()) return list.get(0).getComponent(fieldClass.asSubclass(GameComponent.class));
		}
		return null;
	}
	
}
