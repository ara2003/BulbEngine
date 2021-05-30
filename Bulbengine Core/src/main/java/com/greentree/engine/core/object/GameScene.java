package com.greentree.engine.core.object;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.logger.Logger;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.layer.Layer;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.system.collection.SimpleGroupSystemCollection;
import com.greentree.engine.core.system.collection.SystemCollection;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {

	private final static class Validator {

		public static boolean checkRequire(final Iterable<GameSystem> systems) {
			A : for(final Class<? extends GameSystem> requireClases : Validator.getRequireClasses(systems)) {
				for(final Class<? extends GameSystem> clazz : ClassUtil.getClases(systems))
					if(requireClases.isAssignableFrom(clazz)) continue A;
				return false;
			}
			return true;
		}

		public static Class<? extends GameSystem> getBrokRequireClass(final Iterable<GameSystem> components) {
			final Set<Class<? extends GameSystem>> clases = ClassUtil.getClases(components);
			for(final Class<? extends GameSystem> clazz : Validator.getRequireClasses(components))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}

		public static Set<Class<? extends GameSystem>> getRequireClasses(final Iterable<GameSystem> components) {
			final Set<Class<? extends GameSystem>> requireComponents = new HashSet<>();
			for(final GameSystem com : components)
				for(final RequireSystems rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireSystems.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}


		private Validator() {
		}
	}
	static {
		try {
			Log.createFileType("update scene");
		}catch(final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private final Collection<Layer> layers;

	private final SystemCollection systems;

	public GameScene(final String name) {
		super(name);
		systems = new SimpleGroupSystemCollection();
		layers  = new ArrayList<>();
		layers.add(GameCore.getBuilder().getLayer("default"));
	}

	public void addLayer(final Layer layer) {
		layers.add(layer);
	}

	public boolean addSystem(final GameSystem system) {
		return systems.add(system);
	}

	public boolean contains(Class<? extends GameSystem> class1) {
		return systems.containsClass(class1);
	}

	public boolean contains(final Layer layer) {
		return layers.contains(layer);
	}

	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		systems.clear();
		return false;
	}

	public <T extends GameSystem> T getSystem(final Class<T> clazz) {
		return systems.get(clazz);
	}
	@Override
	public void start() {
		if(!Validator.checkRequire(systems.getSystemIterable())) Log.error(
				"system " + Validator.getBrokRequireClass(systems.getSystemIterable()) + " require is not fulfilled \n" + this);

		systems.initSratr();
		for(final GameObject object : childrens) object.initSratr();
	}

	public String toSimpleString() {
		return String.format("GameScene[name=\"%s\"]@%d", name, super.hashCode());
	}

	@Override
	public String toString() {
		return "GameScene [systems=" + systems + " children=" + childrens + "]@" + hashCode();
	}

	@Override
	public void update() {
		Logger.print("update scene", "s %d", System.nanoTime());
		systems.update();
		//		for(final GameObject object : childrens) object.update();TODO
		Logger.print("update scene", "f %d", System.nanoTime());
	}

	@Override
	public void updateUpTreeComponents() {
		allTreeComponents.clear();
		for(final GameObject object : childrens) allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
	}

}
