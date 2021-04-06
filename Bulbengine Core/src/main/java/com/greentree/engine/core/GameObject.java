package com.greentree.engine.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.Log;
import com.greentree.common.collection.ClassTree;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.corutine.Corutine;

public final class GameObject extends GameObjectParent {
	
	private final ClassTree<GameComponent> components;
	private GameObjectParent parent;
	private final Collection<Corutine> corutines;
	
	private GameObject(final GameObject other) {
		super(other.name + " clone");
		this.components = new HashMapClassTree<>();
		this.corutines  = new LinkedList<>();
		if(other.parent == null) throw new IllegalArgumentException("parent dosen\'t be null");
		this.parent = other.parent;
		this.parent.addChildren(this);
		for(GameComponent com : other.components) {
			addComponent(Game.getBuilder().createComponent(com.getClass()));
		}
	}
	
	public GameObject(final String name, final GameObjectParent parent) {
		super(name);
		this.components = new HashMapClassTree<>();
		this.corutines  = new LinkedList<>();
		if(parent == null) throw new IllegalArgumentException("parent dosen\'t be null");
		this.parent = parent;
		parent.addChildren(this);
	}
	
	public boolean addComponent(final GameComponent component) {
		if(this.components.add(component)) {
			component.setObject(this);
			this.updateUpTreeComponents();
			this.tryAddNecessarilySystem(component.getClass());
			return true;
		}
		return false;
	}
	
	@Override
	public GameObject clone() {
		return new GameObject(this);
	}
	
	public void destroy() {
		if(this.isDestroy()) //			throw new RuntimeException("destroy desroed object");
			return;
		this.getParent().removeChildren(this);
		for(final GameComponent component : this.components) component.setObject(null);
		this.components.clear();
		for(final GameObject object : this.childrens) object.destroy();
		this.childrens.clear();
		this.updateUpTreeComponents();
		this.parent = null;
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final Queue<? extends T> list = this.components.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.iterator().next();
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public GameObjectParent getParent() {
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
		return this.getParent() == null || !this.getParent().contains(this);
	}
	
	@Override
	protected void start() {
		if(!Validator.checkRequireComponent(this.components)) Log.error(
			"component " + Validator.getBrokRequireComponentClass(this.components) + " require is not fulfilled \n" + this);
		for(final GameComponent component : this.components) component.initSratr();
		for(final GameObject component : this.childrens) component.start();
	}
	
	public void startCorutine(final Corutine corutine) {
		this.corutines.add(corutine);
	}
	
	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder("[object(").append(this.getName()).append(")@").append(this.hashCode());
		if(!this.components.isEmpty()) {
			final StringBuilder r = new StringBuilder("\ncomponents:");
			for(final Object obj : this.components) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		return res.toString() + "]";
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<?> clazz) {
		this.getScene().tryAddNecessarilySystem(clazz);
	}
	
	@Override
	protected void update() {
		for(final GameObject component : this.childrens) component.update();
		this.corutines.removeIf(Corutine::run);
	}
	
	@Override
	public void updateUpTreeComponents() {
		this.allTreeComponents.clear();
		for(final GameObject object : this.childrens) this.allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
		this.allTreeComponents.addAll(this.components);
		this.parent.updateUpTreeComponents();
	}
	
	private final static class Validator {
		
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
		
		public static Class<? extends GameComponent> getBrokRequireComponentClass(final Iterable<GameComponent> components) {
			final Set<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
			for(final Class<? extends GameComponent> clazz : Validator.getRequireComponentClases(components))
				if(!clases.contains(clazz)) return clazz;
			return null;
		}
		
		public static Set<Class<? extends GameComponent>> getRequireComponentClases(final Iterable<GameComponent> components) {
			final Set<Class<? extends GameComponent>> requireComponents = new HashSet<>();
			for(final GameComponent com : components)
				for(final RequireComponent rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireComponent.class))
					Collections.addAll(requireComponents, rcom.value());
			return requireComponents;
		}
	}
}

