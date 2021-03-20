package com.greentree.engine.object;

import com.greentree.engine.system.GameSystem;
import com.greentree.engine.system.NecessarilySystems;
import com.greentree.util.ClassUtil;
import com.greentree.util.OneClassSet;

/** @author Arseny Latyshev */
public final class GameScene extends GameNode {
	
	private static final long serialVersionUID = 1L;
	private final OneClassSet<GameSystem> systems;
	
	private GameScene(final String name) {
		super(name);
		this.systems = new OneClassSet<>();
	}
	
	public static Builder builder(final String name) {
		return new Builder(name);
	}
	
	private boolean addSystem(final GameSystem system) {
		if(this.systems.add(system)) {
			this.tryAddNecessarilySystem(system.getClass());
			return true;
		}
		return false;
	}
	
	public <T extends GameSystem> T getSystem(final Class<T> clazz) {
		return this.systems.get(clazz);
	}
	
	@Override
	protected void start() {
		super.start();
		for(final GameSystem system : this.systems) system.init();
	}
	
	@Override
	public String toString() {
		return "GameScene [systems=" + this.systems + "]";
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<?> clazz) {
		for(final NecessarilySystems an : ClassUtil.getAllAnnotations(clazz, NecessarilySystems.class))
			for(final Class<?> cl : an.value()) this.addSystem(GameSystem.createSystem(cl));
	}
	
	@Override
	public void update() {
		super.update();
		for(final GameSystem system : this.systems) system.update();
	}
	
	public final static class Builder {
		
		private final GameScene scene;
		
		private Builder(final String name) {
			this.scene = new GameScene(name);
		}
		
		public void addSystem(final GameSystem createSystem) {
			if(this.scene.isInitialized()) throw new IllegalAccessError("add system befor start");
			this.scene.addSystem(createSystem);
		}
		
		public GameScene get() {
			if(this.scene.isInitialized()) throw new IllegalAccessError("build befor start");
			return this.scene;
		}
	}
}
