package com.greentree.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.greentree.engine.component.ComponentList;
import com.greentree.engine.system.GameSystem;
import com.greentree.engine.system.necessarilySystems;
import com.greentree.util.ClassList;
import com.greentree.util.ClassUtil;
import com.greentree.util.Log;
import com.greentree.util.OneClassSet;

public final class GameNode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ClassList<GameComponent> allTreeComponents, myComponents;
	private final OneClassSet<GameSystem> systems;
	private final String name;
	private final List<String> tags;
	private final List<GameNode> nodes;
	private final List<GameNode> nodeToDeletes;
	private GameNode parent;
	
	public GameNode(String name) {
		systems = new OneClassSet<>();
		myComponents = new ClassList<>();
		allTreeComponents = new ClassList<>();
		nodes = new ArrayList<>();
		nodeToDeletes = new ArrayList<>();
		tags = new ArrayList<>();
		this.name = name;
	}
	
	public void addChildren(GameNode node) {
		allTreeComponents.addAll(node.getAllComponents(null));
		if(node.parent != null) Log.error("seconde perant of node " + node);
		node.parent = this;
		nodes.add(node);
		if(parent != null) parent.updateAllTreeComponents();
	}
	
	public void addComponent(GameComponent component) {
		allTreeComponents.add(component);
		myComponents.add(component);
		if(parent != null) parent.updateAllTreeComponents();
	}
	
	public void addSystem(GameSystem system) {
		systems.add(system);
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	private boolean contains(GameNode node) {
		return nodes.contains(node);
	}
	
	public GameNode createNode(String prefab) {
		GameNode node = Game.getBuilder().createNode(prefab);
		addChildren(node);
		node.start();
		return node;
	}
	
	public void destroy() {
		parent.destroy(this);
	}
	
	public void destroy(GameNode node) {
		nodeToDeletes.add(node);
	}
	
	public List<GameNode> findNodes(Predicate<GameNode> predicate) {
		List<GameNode> list = new ArrayList<>();
		nodes.forEach(e-> {
			if(predicate.test(e)) list.add(e);
		});
		return list;
	}
	
	public List<GameNode> findNodesHasComponent(Class<? extends GameComponent> component) {
		return findNodes(obj->obj.hasComponent(component));
	}
	
	public List<GameNode> findNodesWithName(String name) {
		return findNodes(obj->obj.getName().startsWith(name));
	}
	
	public List<GameNode> findNodesWithTag(String tag) {
		return findNodes(obj->obj.hasTag(tag));
	}
	
	public <T extends GameComponent> ComponentList<T> getAllComponents(final Class<T> clazz) {
		return new ComponentList<>(allTreeComponents.get(clazz));
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		List<T> list = myComponents.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.get(0);
	}
	
	public String getName() {
		return name;
	}
	
	public GameNode getParent() {
		return parent;
	}
	
	public <T extends GameSystem> T getSystem(Class<T> clazz) {
		return systems.get(clazz);
	}
	
	public boolean hasComponent(Class<?> clazz) {
		return myComponents.containsClass(clazz);
	}
	
	public boolean hasComponent(GameComponent component) {
		return myComponents.contains(component);
	}
	
	public boolean hasTag(final String tag) {
		return tags.contains(tag);
	}
	
	public boolean isDestroy() {
		if(parent == null) return false;
		return !parent.contains(this);
	}
	
	public void removeChildren(GameNode node) {
		allTreeComponents.removeAll(node.getAllComponents(null));
		node.parent = null;
		nodes.remove(node);
		if(parent != null) parent.updateAllTreeComponents();
	}
	
	public void removeComponent(GameComponent component) {
		allTreeComponents.add(component);
		myComponents.add(component);
		if(parent != null) parent.updateAllTreeComponents();
	}
	
	public void removeSystem(GameSystem system) {
		systems.remove(system);
	}
	
	public void removeTag(String tag) {
		tags.remove(tag);
	}
	
	void start() {
		for(final GameComponent gc : myComponents) gc.awake(this);
		for(final GameSystem system : systems) system.init();
		if(!Validator.checkRequireComponent(myComponents)) Log.error("component "
				+ Validator.getBrokRequireComponentClass(myComponents) + " require is not fulfilled \n" + this);
		for(final GameComponent gc : myComponents) gc.start(this);
		for(final GameNode node : nodes) node.start();
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("[object(").append(name).append(")@").append(hashCode());
		if(!myComponents.isEmpty()) {
			StringBuilder r = new StringBuilder("\ncomponents:");
			for(Object obj : myComponents) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		if(!systems.isEmpty()) {
			StringBuilder r = new StringBuilder("\nsystems:");
			for(Object obj : systems) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		if(!nodes.isEmpty()) {
			StringBuilder r = new StringBuilder("\nnodes:");
			for(Object obj : nodes) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		return res.toString();
	}
	
	public void tryAddNecessarily(Class<?> clazz) {
		for(necessarilySystems an : ClassUtil.getAllAnnotations(clazz, necessarilySystems.class))
			for(Class<?> cl : an.value()) {
				GameSystem system = GameSystem.createSystem(cl);
				systems.add(system);
			}
	}
	
	public void update() {
		for(final GameNode nodeToDelete : nodeToDeletes) {
			nodes.remove(nodeToDelete);
			allTreeComponents.removeAll(nodeToDelete.getAllComponents(null));
			updateAllTreeComponents();
		}
		for(final GameSystem system : systems) system.execute();
		for(final GameNode node : nodes) node.update();
		for(final GameComponent c : myComponents) c.update0();
	}
	
	private void updateAllTreeComponents() {
		allTreeComponents.clear();
		for(GameNode node : nodes) {
			allTreeComponents.addAll(node.getAllComponents(GameComponent.class));
		}
		if(parent != null) parent.updateAllTreeComponents();
	}
}
