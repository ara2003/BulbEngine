package com.greentree.engine.core.object;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.WeakClassTree;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.component.NewComponentEvent;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.util.Events;

public final class GameObject extends GameObjectParent {

	private final WeakClassTree<GameComponent> components;
	private final GameObjectParent parent;

	public GameObject(final String name, final GameObjectParent parent) {
		super(name);
		components = new HashMapClassTree<>();
		if(parent == null) throw new IllegalArgumentException(this + "parent dosen\'t be null");
		if(checkParent(parent)) throw new IllegalArgumentException(this + " object cannot be its parent");
		this.parent = parent;
		parent.addChildren(this);
	}

	public boolean addComponent(final GameComponent component) {
		if(components.add(component, component.getClass())) {
			component.setObject(this);
			updateUpTreeComponents();
			return true;
		}
		return false;
	}

	private boolean checkParent(GameObjectParent parent2) {
		if(parent2 instanceof GameObject) {
			GameObject parent = (GameObject) parent2;
			if(parent2 == this)return true;
			return checkParent(parent.getParent());
		}else
			return false;
	}

	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		for(final GameComponent component : components) component.destroy();
		components.clear();
		allTreeComponents.clear();
		updateUpTreeComponents();
		return false;
	}

	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final List<? extends T> list = components.get(clazz);
		if(list.isEmpty()) {
			Log.info("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.iterator().next();
	}

	@Override
	public String getName() {
		return name;
	}

	public GameObjectParent getParent() {
		return parent;
	}

	public GameScene getScene() {
		if(parent instanceof GameScene) return (GameScene) parent;
		return ((GameObject) parent).getScene();
	}

	public boolean hasComponent(final Class<? extends GameComponent> clazz) {
		return components.containsClass(clazz);
	}

	public boolean hasComponent(final GameComponent component) {
		return components.contains(component);
	}

	public void removeComponent(final GameComponent gameComponent) {
		components.remove(gameComponent);
	}

	@Override
	protected void start() {
		if(!Validator.checkRequireComponent(components)) Log.error(
				"component " + Validator.getBrokRequireComponentClass(components) + " require is not fulfilled \n" + this);

		if(!Validator.checkRequireSystem(components, getScene())) Log.error(
				"component " + Validator.getBrokRequireSystemClass(components, getScene()) + " require is not fulfilled \n" + this);

		for(final GameObject obj : childrens) obj.initSratr();
		for(final GameComponent component : components) Events.event(new NewComponentEvent(component));
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder("[object(").append(getName()).append(")@").append(super.hashCode());
		if(!components.isEmpty()) {
			final StringBuilder r = new StringBuilder("\ncomponents:");
			for(final GameComponent component : components) r.append("\n").append(component.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		return res.toString() + "]";
	}

	@Override
	public void updateUpTreeComponents() {
		allTreeComponents.clear();
		for(final GameObject object : childrens) allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
		allTreeComponents.addAll(components);
		parent.updateUpTreeComponents();
	}

	private final static class Validator {

		private Validator() {
		}

		public static boolean checkRequireComponent(final Iterable<GameComponent> components) {
			A : for(final Class<? extends GameComponent> requireClases : Validator.getRequireComponentClasses(components)) {
				for(final Class<? extends GameComponent> clazz : ClassUtil.getClases(components))
					if(requireClases.isAssignableFrom(clazz)) continue A;
				return false;
			}
			return true;
		}

		public static boolean checkRequireSystem(final Iterable<GameComponent> components, final GameScene scene) {
			for(final Class<? extends MultiBehaviour> requireClass : Validator.getRequireSystemClasses(components)) if(!scene.contains(requireClass))return false;
			return true;
		}

		public static Class<? extends GameComponent> getBrokRequireComponentClass(final Iterable<GameComponent> components) {
			final Collection<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
			for(final Class<? extends GameComponent> clazz : Validator.getRequireComponentClasses(components))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}

		public static Class<? extends MultiBehaviour> getBrokRequireSystemClass(final Iterable<GameComponent> components, final GameScene scene) {
			for(final Class<? extends MultiBehaviour> clazz : Validator.getRequireSystemClasses(components))
				if(!scene.contains(clazz)) return clazz;
			return null;
		}

		public static Set<Class<? extends GameComponent>> getRequireComponentClasses(final Iterable<GameComponent> components) {
			final Set<Class<? extends GameComponent>> requireComponents = new HashSet<>();
			for(final GameComponent com : components)
				for(final RequireComponent rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireComponent.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}

		public static Collection<Class<? extends MultiBehaviour>> getRequireSystemClasses(final Iterable<GameComponent> components) {
			final Collection<Class<? extends MultiBehaviour>> requireComponents = new HashSet<>();
			for(final GameComponent com : components)
				for(final RequireSystems rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireSystems.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}
	}
}

