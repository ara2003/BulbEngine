package com.greentree.engine.core.object;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.WeakClassTree;

/** @author Arseny Latyshev */
public abstract class GameObjectParent {

	private boolean isDestoy = false;
	private boolean isStart = false;
	protected final Collection<GameObject> childrens;


	protected final WeakClassTree<GameComponent> allTreeComponents;

	protected final String name;

	public GameObjectParent(final String name) {
		this.name         = name;
		allTreeComponents = new HashMapClassTree<>();
		childrens         = new CopyOnWriteArraySet<>();
	}
	public final boolean addChildren(final GameObject object) {
		if(childrens.add(object)) {
			if(object.isEmpty())return true;
			updateUpTreeComponents();
			return true;
		}
		return false;
	}
	public final boolean contains(final GameObject node) {
		return childrens.contains(node);
	}


	public final void destroy() {
		if(!isDestoy) {
			destroy_full();
			isDestoy = true;
		}
	}

	protected void destroy_full() {
		for(final GameObject obj : childrens) obj.destroy_one();
		childrens.clear();
	}

	public final List<GameObject> findMyObjects(final Predicate<GameObject> predicate) {
		return childrens.parallelStream().filter(predicate).collect(Collectors.toList());
	}

	public final List<GameObject> findObjects(final Predicate<GameObject> predicate) {
		final List<GameObject> res = findMyObjects(predicate);
		for(final GameObject object : childrens) res.addAll(object.findObjects(predicate));
		return res;
	}

	public final List<GameObject> findObjectsHasComponent(final Class<? extends GameComponent> component) {
		return findObjects(obj->obj.hasComponent(component));
	}

	public final List<GameObject> findObjectsWithName(final String name) {
		return findObjects(obj->obj.getName().startsWith(name));
	}

	public final <T> List<T> getAllComponents(final Class<T> clazz) {
		return allTreeComponents.get(clazz);
	}

	public String getName() {
		return name;
	}

	public final void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
		start();
	}

	public final boolean isDestroy() {
		return isDestoy;
	}

	public final void removeChildren(final GameObject object) {
		childrens.remove(object);
		updateUpTreeComponents();
	}

	protected abstract void start();

	public abstract String toSimpleString();

	protected void update() {
	}
	public abstract void updateUpTreeComponents();

}
