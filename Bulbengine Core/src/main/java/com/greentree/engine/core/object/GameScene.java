package com.greentree.engine.core.object;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.logger.Logger;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.util.SceneLoader;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {

	private final SystemCollection systems;
	static {
		try {
			Log.createFileType("update scene");
		}catch(final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public GameScene(final String name) {
		super(name);
		systems = new SystemCollection();
	}

	public boolean addSystem(final GameSystem system) {
		return systems.add(system);
	}

	public boolean contains(Class<? extends MultiBehaviour> class1) {
		return systems.containsClass(class1);
	}


	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		systems.clear();
		return false;
	}

	public <T extends MultiBehaviour> GameSystem getSystem(final Class<T> clazz) {
		return systems.get(clazz);
	}

	public boolean isCurrent() {
		return this == SceneLoader.getCurrentScene();
	}

	@Override
	public void start() {
		if(!Validator.checkRequire(systems)) Log.error(
				"system " + Validator.getBrokRequireClass(systems) + " require is not fulfilled \n" + this);

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

	private final class SystemCollection extends CopyOnWriteArrayList<GameSystem> {
		private static final long serialVersionUID = 1L;

		public <S extends MultiBehaviour> boolean containsClass(final Class<S> clazz) {
			return null != get(clazz);
		}

		public <S extends MultiBehaviour> GameSystem get(final Class<S> clazz) {
			for(GameSystem s : this) if(s.getBehaviour().getClass().isAssignableFrom(clazz))return s;
			return null;
		}

		public void initSratr() {
			forEach(GameSystem::initSratr);
		}

		public void update() {
			forEach(s -> s.getBehaviour().update());
		}

	}

	private final static class Validator {

		private Validator() {
		}

		public static boolean checkRequire(final Collection<GameSystem> systems) {
			A : for(final Class<? extends MultiBehaviour> requireClases : Validator.getRequireClasses(systems)) {
				for(final Class<? extends MultiBehaviour> clazz :  ClassUtil.getClases(systems, GameSystem::getBehaviour))
					if(requireClases.isAssignableFrom(clazz)) continue A;
				return false;
			}
			return true;
		}

		public static Class<? extends MultiBehaviour> getBrokRequireClass(final Collection<GameSystem> systems) {
			final Collection<Class<? extends MultiBehaviour>> clases = ClassUtil.getClases(systems, GameSystem::getBehaviour);
			for(final Class<? extends MultiBehaviour> clazz : Validator.getRequireClasses(systems))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}


		public static Collection<Class<? extends MultiBehaviour>> getRequireClasses(final Collection<GameSystem> components) {
			final Collection<Class<? extends MultiBehaviour>> requireComponents = new HashSet<>();
			for(final GameSystem com : components)
				for(final RequireSystems rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireSystems.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}
	}

}
