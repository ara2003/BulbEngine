package com.greentree.engine.core.object;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.WeakClassTree;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.component.NewComponentEvent;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.layer.Layer;
import com.greentree.engine.core.system.RequireSystems;

public final class GameObject extends GameObjectParent {
	
	private final WeakClassTree<GameComponent> components;
	private GameObjectParent parent;
	private Layer layer;
	
	public GameObject(final String name, final Layer layer, final GameObjectParent parent) {
		super(name);
		components = new HashMapClassTree<>();
		if(parent == null) throw new IllegalArgumentException("parent dosen\'t be null");
		this.parent = parent;
		if(!getScene().contains(layer)) throw new IllegalArgumentException("the scene does not contain layer [name=\"" + layer.getName() + "\"] settings");
		this.layer = layer;
		parent.addChildren(this);
	}
	
	public boolean addComponent(final GameComponent component) {
		if(components.add(component)) {
			component.setObject(this);
			updateUpTreeComponents();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		for(final GameComponent component : components) component.destroy();
		components.clear();
		for(final GameObject object : childrens) object.destroy();
		childrens.clear();
		getParent().removeChildren(this);
		updateUpTreeComponents();
		parent = null;
		layer  = null;
		return false;
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final List<? extends T> list = components.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.iterator().next();
	}
	
	public Layer getLayer() {
		return layer;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public GameObjectParent getParent() {
		return parent;
	}
	
	public GameScene getScene() {
		if(getParent() instanceof GameScene) return (GameScene) parent;
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
	
	protected void start() {
		if(!Validator.checkRequireComponent(components)) Log.error(
			"component " + Validator.getBrokRequireComponentClass(components) + " require is not fulfilled \n" + this);
		
		if(!Validator.checkRequireSystem(components, getScene())) Log.error(
			"component " + Validator.getBrokRequireSystemClass(components, getScene()) + " require is not fulfilled \n" + this);

		for(final GameComponent component : components) Events.event(new NewComponentEvent(component));
		for(final GameObject component : childrens) component.start();
	}
	
	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder("[object(").append(getName()).append(")@").append(hashCode());
		if(!components.isEmpty()) {
			final StringBuilder r = new StringBuilder("\ncomponents:");
			for(final Object obj : components) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		return res.toString() + "]";
	}
	
	@Override
	@Deprecated
	protected void update() {
		for(final GameObject component : childrens) component.update();
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
			A : for(final Class<? extends GameSystem> requireClass : Validator.getRequireSystemClasses(components)) {
				if(scene.contains(requireClass)) continue A;
				return false;
			}
			return true;
		}
		
		public static Class<? extends GameComponent> getBrokRequireComponentClass(final Iterable<GameComponent> components) {
			final Set<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
			for(final Class<? extends GameComponent> clazz : Validator.getRequireComponentClasses(components))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}
		
		public static Class<? extends GameSystem> getBrokRequireSystemClass(final Iterable<GameComponent> components, final GameScene scene) {
			for(final Class<? extends GameSystem> clazz : Validator.getRequireSystemClasses(components))
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
		
		public static Set<Class<? extends GameSystem>> getRequireSystemClasses(final Iterable<GameComponent> components) {
			final Set<Class<? extends GameSystem>> requireComponents = new HashSet<>();
			for(final GameComponent com : components)
				for(final RequireSystems rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireSystems.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}
	}
}

