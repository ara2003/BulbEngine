package com.greentree.engine.core.object;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.action.EventAction;
import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {

	private final GameScene parent;
	private final SystemCollection systems  = new SystemCollection();
	private final EventAction<GameComponent> newComponent = new EventAction<>();

	public GameScene(final String name) {
		super(name);
		parent = null;
	}

	public GameScene(final String name, GameScene parent) {
		super(name);
		this.parent = parent;
		newComponent.addListener(c -> parent.newComponent.action(c));
	}

	public boolean addSystem(final GameSystem<?> system) {
		return systems.add(system);
	}

	public boolean contains(Class<? extends MultiBehaviour> class1) {
		return systems.containsClass(class1) || (parent != null&&parent.contains(class1));
	}

	@Override
	protected void destroy_full() {
		super.destroy_full();
		systems.destroy();
		allTreeComponents.clear();
	}

	public EventAction<GameComponent> getNewComponentAction() {
		return newComponent;
	}

	public <T extends MultiBehaviour> GameSystem<T> getSystem(final Class<T> clazz) {
		var res = systems.get(clazz);
		if(res != null)return res;
		if(parent != null)return parent.getSystem(clazz);
		return null;
	}

	@Override
	public void start() {
		if(!Validator.checkRequire(systems)) Log.error(
				"system " + Validator.getBrokRequireClass(systems) + " require is not fulfilled \n" + this);

		systems.initSratr();
		for(final GameObject object : childrens) object.initSratr();

	}

	@Override
	public String toSimpleString() {
		return String.format("GameScene(%s)@%d", name, super.hashCode()) + ((parent == null)?"":" -> "+parent.toSimpleString());
	}

	@Override
	public String toString() {
		return toSimpleString() + " [systems=" + systems + " children=" + childrens + "]";
	}

	@Override
	public void update() {
		systems.update();
		if(parent != null)parent.update();
	}

	@Override
	public void updateUpTreeComponents() {
		allTreeComponents.clear();
		for(final GameObject object : childrens) allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
		if(parent != null)parent.updateUpTreeComponents();
	}

	private final static class SystemCollection extends CopyOnWriteArrayList<GameSystem<?>> {
		private static final long serialVersionUID = 1L;

		public <S extends MultiBehaviour> boolean containsClass(final Class<S> clazz) {
			return null != get(clazz);
		}

		public void destroy() {
			forEach(GameSystem::initDestroy);
			clear();
		}

		@SuppressWarnings("unchecked")
		public <B extends MultiBehaviour> GameSystem<B> get(final Class<B> clazz) {
			for(GameSystem<?> s : this) if(s.getBehaviour().getClass().isAssignableFrom(clazz))return (GameSystem<B>) s;
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

		public static boolean checkRequire(final Collection<GameSystem<?>> systems) {
			A : for(final Class<? extends MultiBehaviour> requireClases : Validator.getRequireClasses(systems)) {
				for(final Class<? extends MultiBehaviour> clazz :  ClassUtil.getClases(systems, GameSystem::getBehaviour))
					if(requireClases.isAssignableFrom(clazz)) continue A;
				return false;
			}
			return true;
		}

		public static Class<? extends MultiBehaviour> getBrokRequireClass(final Collection<GameSystem<?>> systems) {
			final Collection<Class<? extends MultiBehaviour>> clases = ClassUtil.getClases(systems, GameSystem::getBehaviour);
			for(final Class<? extends MultiBehaviour> clazz : Validator.getRequireClasses(systems))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}


		public static Collection<Class<? extends MultiBehaviour>> getRequireClasses(final Collection<GameSystem<?>> components) {
			final Collection<Class<? extends MultiBehaviour>> requireComponents = new HashSet<>();
			for(final GameSystem<?> com : components)
				for(final RequireSystems rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireSystems.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}
	}

}
