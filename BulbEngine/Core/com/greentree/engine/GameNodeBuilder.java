package com.greentree.engine;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.system.util.GameSystem;

/**
 * @author Arseny Latyshev
 *
 */
public class GameNodeBuilder {

	private final String name;
	private GameNode parent;
	private final List<String> tags;
	private final List<GameComponent> components;
	private final List<GameNode> nodes;
	private final List<GameSystem> systems;
	private GameNode res;
	
	public GameNodeBuilder(String name){
		this.name = name;
		tags = new ArrayList<>(1);
		components = new ArrayList<>();
		nodes = new ArrayList<>();
		systems = new ArrayList<>(0);
	}
	
	public GameNode get(){
		res = new GameNode(name, parent, tags, components, nodes, systems);
		return res;
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}

	public void addComponent(GameComponent component) {
		components.add(component);
	}
	
	public void setParent(GameNode parent) {
		this.parent = parent;
	}

	public void addSystem(GameSystem system) {
		systems.add(system);
	}

	public void addNode(GameNode node) {
		nodes.add(node);
	}
	
	
}
