package com.greentree.engine.object;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.greentree.collection.ClassTree;
import com.greentree.collection.HashMapClassTree;
import com.greentree.engine.Game;
import com.greentree.engine.component.ComponentList;
import com.greentree.engine.component.GameComponent;

/** @author Arseny Latyshev */
public abstract class GameNode extends GameElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected final String name;
	private final Set<GameObject> childrens;
	private final ClassTree<GameComponent> allTreeComponents;
	
	public GameNode(final String name) {
		this.name              = name;
		this.allTreeComponents = new HashMapClassTree<>();
		this.childrens         = new CopyOnWriteArraySet<>();
	}
	
	protected final void addChildren(final GameObject children) {
		this.childrens.add(children);
	}
	
	protected final void addComponentsToTree(final ClassTree<GameComponent> components) {
		this.allTreeComponents.addAll(components);
	}
	
	public final boolean contains(final GameObject node) {
		return childrens.contains(node);
	}
	
	@Override
	public final GameObject createObject(final String prefab) {
		return Game.getBuilder().createObject(prefab, this);
	}
	
	public final List<GameObject> findMyObjects(final Predicate<GameObject> predicate) {
		return this.childrens.parallelStream().filter(predicate).collect(Collectors.toList());
	}
	
	public final List<GameObject> findObjects(final Predicate<GameObject> predicate) {
		List<GameObject> res = findMyObjects(predicate);
		for(GameObject object : childrens)res.addAll(object.findObjects(predicate));
		return res;
	}
	
	public final List<GameObject> findObjectsHasComponent(final Class<? extends GameComponent> component) {
		return this.findObjects(obj->obj.hasComponent(component));
	}
	
	public final List<GameObject> findObjectsWithName(final String name) {
		return this.findObjects(obj->obj.getName().startsWith(name));
	}
	
	public final <T> Set<T> getAllComponents(final Class<T> clazz) {
		return this.allTreeComponents.get(clazz);
	}
	
	public final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return new ComponentList<>(this.getAllComponents(clazz));
	}
	
	protected final void removeChildren(final GameObject object) {
		this.childrens.remove(object);
		updateUpTreeComponents();
	}
	
	@Override
	protected void start() {
		for(final GameObject children : this.childrens) children.init();
		updateUpTreeComponents();
	}
	
	public abstract void tryAddNecessarilySystem(Class<? extends GameElement> clazz);
	
	@Override
	public void update() {
		for(final GameObject object : this.childrens) object.update();
	}
	
	public void updateUpTreeComponents() {
		this.allTreeComponents.clear();
		for(final GameObject object : this.childrens)
			this.allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
	}
}
