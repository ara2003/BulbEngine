package com.greentree.engine.editor.xml;

import java.util.List;

import com.greentree.engine.Game;
import com.greentree.engine.component.GameComponent;
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
