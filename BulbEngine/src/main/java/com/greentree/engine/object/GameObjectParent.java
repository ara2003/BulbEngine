package com.greentree.engine.object;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.greentree.common.collection.ClassTree;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.engine.component.ComponentList;

/** @author Arseny Latyshev */
public abstract class GameObjectParent extends GameElement {
	
	protected static final Random random = new Random();
	protected final Set<GameObject> childrens;
	protected final ClassTree<GameComponent> allTreeComponents;
	protected final String name;
	
	public GameObjectParent(final String name) {
		this.name              = name;
		this.allTreeComponents = new HashMapClassTree<>();
		this.childrens         = new CopyOnWriteArraySet<>();
	}
	
	public final boolean addChildren(final GameObject object) {
		if(this.childrens.add(object)) {
			this.updateUpTreeComponents();
			return true;
		}
		return false;
	}
	
	public final boolean contains(final GameObject node) {
		return this.childrens.contains(node);
	}
	
	public final List<GameObject> findMyObjects(final Predicate<GameObject> predicate) {
		return this.childrens.parallelStream().filter(predicate).collect(Collectors.toList());
	}
	
	public final List<GameObject> findObjects(final Predicate<GameObject> predicate) {
		final List<GameObject> res = this.findMyObjects(predicate);
		for(final GameObject object : this.childrens) res.addAll(object.findObjects(predicate));
		return res;
	}
	
	public final List<GameObject> findObjectsHasComponent(final Class<? extends GameComponent> component) {
		return this.findObjects(obj->obj.hasComponent(component));
	}
	
	public final List<GameObject> findObjectsWithName(final String name) {
		return this.findObjects(obj->obj.getName().startsWith(name));
	}
	
	public final <T> Queue<T> getAllComponents(final Class<T> clazz) {
		return this.allTreeComponents.get(clazz);
	}
	
	public final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return new ComponentList<>(this.getAllComponents(clazz));
	}
	
	public String getName() {
		return this.name;
	}
	
	public final void removeChildren(final GameObject object) {
		this.childrens.remove(object);
		this.updateUpTreeComponents();
	}
	
	public abstract void tryAddNecessarilySystem(Class<?> clazz);
	public abstract void updateUpTreeComponents();
}
