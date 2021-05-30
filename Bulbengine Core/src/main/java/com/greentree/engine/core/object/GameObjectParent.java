package com.greentree.engine.core.object;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.WeakClassTree;
import com.greentree.engine.core.component.ComponentList;

/** @author Arseny Latyshev */
public abstract class GameObjectParent extends StartGameElement {

	protected static final Random random = new Random();
	protected final Set<GameObject> childrens;
	protected final WeakClassTree<GameComponent> allTreeComponents;
	protected final String name;


	public GameObjectParent(final String name) {
		this.name         = name;
		allTreeComponents = new HashMapClassTree<>();
		childrens         = new CopyOnWriteArraySet<>();
	}

	public final boolean addChildren(final GameObject object) {
		if(childrens.add(object)) {
			updateUpTreeComponents();
			return true;
		}
		return false;
	}

	public final boolean contains(final GameObject node) {
		return childrens.contains(node);
	}

	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		for(final GameObject obj : childrens) obj.destroy();
		childrens.clear();
		allTreeComponents.clear();
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

	public final void removeChildren(final GameObject object) {
		childrens.remove(object);
		updateUpTreeComponents();
	}

	protected void update() {
	}

	public abstract void updateUpTreeComponents();

}
