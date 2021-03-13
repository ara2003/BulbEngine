package com.greentree.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.greentree.engine.component.RequireComponent;
import com.greentree.util.ClassUtil;

/** @author Arseny Latyshev */
public final class Validator {
	
	private Validator() {
	}
	
	public static boolean checkRequireComponent(Collection<GameComponent> components) {
		A : for(Class<? extends GameComponent> requireClases : getRequireComponentClases(components)) {
			for(Class<? extends GameComponent> clazz : ClassUtil.getClases(components)) {
				if(requireClases.isAssignableFrom(clazz)) continue A;
			}
			return false;
		}
		return true;
	}
	
	public static Class<? extends GameComponent> getBrokRequireComponentClass(Collection<GameComponent> components) {
		Set<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
		for(Class<? extends GameComponent> clazz : getRequireComponentClases(components)) {
			if(!clases.contains(clazz)) return clazz;
		}
		return null;
	}
	
	public static Set<Class<? extends GameComponent>> getRequireComponentClases(Collection<GameComponent> components) {
		final Set<Class<? extends GameComponent>> requireComponents = new HashSet<>();
		for(GameComponent com : components)
			for(RequireComponent rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireComponent.class))
				for(Class<? extends GameComponent> clazz : rcom.value()) {
					requireComponents.add(clazz);
				}
		return requireComponents;
	}
}
