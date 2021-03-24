package com.greentree.engine.object;

import static com.greentree.engine.object.GameElement.*;

import com.greentree.common.ClassUtil;
import com.greentree.common.collection.OneClassSet;
import com.greentree.engine.system.NecessarilySystems;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {
	
	private final OneClassSet<GameSystem> systems;
	
	public GameScene(final String name) {
		super(name);
		this.systems = new OneClassSet<>();
	}
	
	public boolean addSystem(final GameSystem system) {
		return this.systems.add(system);
	}
	
	public <T extends GameSystem> T getSystem(final Class<T> clazz) {
		return this.systems.get(clazz);
	}
	
	@Override
	public String toString() {
		return "GameScene [systems=" + this.systems + " children=" + childrens + "]@"+hashCode();
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<?> clazz) {
		for(final NecessarilySystems an : ClassUtil.getAllAnnotations(clazz, NecessarilySystems.class))
			for(final Class<? extends GameSystem> cl : an.value()) this.addSystem(GameSystem.createSystem(cl));
	}
	
	@Override
	public void start() {
		for(GameSystem system : systems)system.initSratr();
		for(GameObject object : childrens)object.initSratr();
	}
	
	@Override
	public void update() {
		for(GameSystem system : systems)system.update();
		for(GameObject object : childrens)object.update();
	}
	
	@Override
	public void updateUpTreeComponents() {
		this.allTreeComponents.clear();
		for(final GameObject object : this.childrens) this.allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
	}
}
