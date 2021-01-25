package com.greentree.engine;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.greentree.engine.component.util.ComponentList;
import com.greentree.engine.event.ListenerManager;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.util.ClassList;
import com.greentree.util.OneClassSet;

public final class GameNode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ClassList<GameComponent> allTreeComponents, myComponents;
	private final OneClassSet<GameSystem> systems;
	private final String name;
	private final List<String> tags;
	private final List<GameNode> nodes;
	private GameNode parent;
	
	protected GameNode(String name, GameNode parent, List<String> tags, List<GameComponent> component, List<GameNode> nodes, List<GameSystem> systems) {
		this.systems = new OneClassSet<>(systems);
		myComponents = new ClassList<>(component);
		allTreeComponents = new ClassList<>(component);
		this.nodes = nodes;
		this.tags = tags;
		this.name = name;
		this.parent = parent;
		for(GameNode node : nodes) {
			addNode(node);
		}
	}
	
	
	private void addNode(GameNode node) {
		allTreeComponents.addAll(node.getComponents(null));
		node.parent = this;
	}

	private boolean contains(GameNode node) {
		return nodes.contains(node);
	}
	
	public GameNode createNode(String prefab) {
		GameNode node = Game.getBuilder().createNode(prefab);
		nodes.add(node);
		addNode(node);
		node.start();
		return node;
	}
	
	public void destroy() {
		parent.destroy(this);
	}

	public List<GameNode> findNodes(Predicate<GameNode> predicate){
		List<GameNode> list = new ArrayList<>();
		nodes.forEach(e -> {
			if(predicate.test(e))list.add(e);
		});
		return list;
	}
	public void destroy(GameNode node) {
		nodes.remove(node);
		allTreeComponents.removeAll(node.getComponents(null));
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
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		List<T> list = myComponents.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.get(0);
	}
	
	public <T extends GameComponent> ComponentList<T> getComponents(final Class<T> clazz) {
		if(clazz == null)return new ComponentList<T>(myComponents.get(null));
		return new ComponentList<>(allTreeComponents.get(clazz));
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
		if(parent == null)return false;
		return !parent.contains(this);
	}
	
	void start() {
		for(final GameComponent gc : myComponents)gc.start(this);
		for(final GameNode node : nodes)node.start();
	}
	
	@Override
	public String toString() {
		String res = "[object(" + name + ")@"+hashCode();
		if(!myComponents.isEmpty()){
    		String r = "\ncomponents:";
    		for(Object obj : myComponents) r += "\n"+obj.toString();
    		res += r.replace("\n", "\n\t");
		}
		if(!systems.isEmpty()){
    		String r = "\nsystems:";
    		for(Object obj : systems) r += "\n"+obj.toString();
    		res += r.replace("\n", "\n\t");
		}
		if(!nodes.isEmpty()){
    		String r = "\nnodes:";
    		for(Object obj : nodes) r += "\n"+obj.toString();
    		res += r.replace("\n", "\n\t");
		}
		return res;
	}
	
	public void tryAddNecessarily(Class<?> clazz) {
		for(necessarily an : ClassUtil.getAnnotations(clazz, necessarily.class))
			for(Class<?> cl : an.value()) {
				if(GameSystem.class.isAssignableFrom(cl)) {
					GameSystem system = GameSystem.createSystem(cl);
					systems.add(system);
					system.init();
				}
				if(ListenerManager.class.isAssignableFrom(cl)) {
					try {
						Game.getEventSystem().addListenerManager((ListenerManager) cl.getConstructor().newInstance());
					}catch(InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	public void update() {
		for(final GameSystem system : systems)system.execute();
		for(final GameNode node : nodes)node.update();
		for(final GameComponent c : myComponents) c.update0();
	}
	
}
