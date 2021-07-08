package com.greentree.engine.core.object;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.WeakClassTree;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.component.GameComponent;

/** @author Arseny Latyshev */
public abstract class GameObjectParent {

	private boolean isDestoy = false;

	private static int ids;
	protected final int id;
	private boolean isStart = false;


	protected final Collection<GameObject> childrens;

	protected final WeakClassTree<GameComponent> allTreeComponents;

	protected final String name;
	
	public GameObjectParent(final String name) {
		id = ids++;
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


	public boolean destroy() {
		if(isDestroy()) return true;
		isDestoy = true;
		for(final GameObject obj : childrens) obj.destroy();
		childrens.clear();
		return false;
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

	public final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return new ComponentList<>(this.getAllComponents(clazz));
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

	protected void update() {
	}

	public abstract void updateUpTreeComponents();
	public abstract String toSimpleString();

}
