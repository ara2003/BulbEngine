package com.greentree.engine.object;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.greentree.collection.ClassTree;
import com.greentree.collection.HashMapClassTree;
import com.greentree.engine.component.GameComponent;
import com.greentree.engine.component.RequireComponent;
import com.greentree.util.ClassUtil;
import com.greentree.util.Log;

public final class GameObject extends GameNode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ClassTree<GameComponent> components;
	private final GameNode parent;
	
	private GameObject(final String name, final GameNode parent) {
		super(name);
		if(parent == null) throw new NullPointerException("parent is null");
		this.parent     = parent;
		this.components = new HashMapClassTree<>();
		parent.addChildren(this);
	}
	
	public static Builder builder(final String name, final GameNode parent) {
		return new Builder(name, parent);
	}
	
	private void addComponent(final GameComponent component) {
		this.components.add(component);
	}
	
	public void destroy() {
		this.getParent().removeChildren(this);
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final Set<? extends T> list = this.components.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.iterator().next();
	}
	
	public String getName() {
		return this.name;
	}
	
	public GameNode getParent() {
		return this.parent;
	}
	
	public GameScene getScene() {
		if(this.getParent() instanceof GameScene) return (GameScene) this.parent;
		return ((GameObject) this.parent).getScene();
	}
	
	public boolean hasComponent(final Class<? extends GameComponent> clazz) {
		return this.components.containsClass(clazz);
	}
	
	public boolean hasComponent(final GameComponent component) {
		return this.components.contains(component);
	}
	
	public boolean isDestroy() {
		return !this.getParent().contains(this);
	}
	
	@Override
	protected void start() {
		if(!Validator.checkRequireComponent(this.components)) Log.error("component "
				+ Validator.getBrokRequireComponentClass(this.components) + " require is not fulfilled \n" + this);
		for(final GameComponent gc : this.components) gc.awake0(this);
		for(final GameComponent gc : this.components) gc.init();
		super.start();
	}
	
	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder("[object(").append(this.name).append(")@").append(this.hashCode());
		if(!this.components.isEmpty()) {
			final StringBuilder r = new StringBuilder("\ncomponents:");
			for(final Object obj : this.components) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		/*
		if(!systems.isEmpty()) {
			StringBuilder r = new StringBuilder("\nsystems:");
			for(Object obj : systems) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		if(!childrens.isEmpty()) {
			StringBuilder r = new StringBuilder("\nnodes:");
			for(Object obj : childrens) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		*/
		return res.toString() + "]";
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<? extends GameElement> clazz) {
		this.getScene().tryAddNecessarilySystem(clazz);
	}
	
	@Override
	public void update() {
		for(final GameComponent c : this.components) c.update0();
		super.update();
	}
	
	@Override
	public void updateUpTreeComponents() {
		super.updateUpTreeComponents();
		this.addComponentsToTree(this.components);
		this.parent.updateUpTreeComponents();
	}
	
	public final static class Builder {
		
		GameObject object;
		
		private Builder(final String name, final GameNode parent) {
			this.object = new GameObject(name, parent);
		}
		
		public void addComponent(final GameComponent component) {
			object.getScene().tryAddNecessarilySystem(component.getClass());
			this.object.addComponent(component);
		}
		
		public GameObject get() {
			if(this.object.isInitialized()) throw new IllegalAccessError("build befor start");
			object.updateUpTreeComponents();
			return this.object;
		}
	}
	
	public void updateTreeComponents() {
	}
}

final class Validator {
	
	private Validator() {
	}
	
	public static boolean checkRequireComponent(final Iterable<GameComponent> components) {
		A : for(final Class<? extends GameComponent> requireClases : Validator.getRequireComponentClases(components)) {
			for(final Class<? extends GameComponent> clazz : ClassUtil.getClases(components))
				if(requireClases.isAssignableFrom(clazz)) continue A;
			return false;
		}
		return true;
	}
	
	public static Class<? extends GameComponent> getBrokRequireComponentClass(
			final Iterable<GameComponent> components) {
		final Set<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
		for(final Class<? extends GameComponent> clazz : Validator.getRequireComponentClases(components))
			if(!clases.contains(clazz)) return clazz;
		return null;
	}
	
	public static Set<Class<? extends GameComponent>> getRequireComponentClases(
			final Iterable<GameComponent> components) {
		final Set<Class<? extends GameComponent>> requireComponents = new HashSet<>();
		for(final GameComponent com : components)
			for(final RequireComponent rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireComponent.class))
				Collections.addAll(requireComponents, rcom.value());
		return requireComponents;
	}
}
