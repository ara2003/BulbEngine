package com.greentree.engine.core.object;

import java.io.FileNotFoundException;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.logger.Logger;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.core.system.collection.SimpleGroupSystemCollection;
import com.greentree.engine.core.system.collection.SystemCollection;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {
	
	private final SystemCollection systems;
	static {
		try {
			Log.createFileType("update scene");
		}catch(final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public GameScene(final String name) {
		super(name);
		systems = new SimpleGroupSystemCollection();
	}
	
	public boolean addSystem(final GameSystem system) {
		return systems.add(system);
	}
	
	@Override
	public boolean destroy() {
		if(super.destroy()) return true;
		systems.clear();
		return false;
	}
	
	public <T extends GameSystem> T getSystem(final Class<T> clazz) {
		return systems.get(clazz);
	}
	
	@Override
	public void start() {
		systems.initSratr();
		for(final GameObject object : childrens) object.initSratr();
	}
	
	public String toSimpleString() {
		return String.format("GameScene[name=\"%s\"]@%d", name, super.hashCode());
	}
	
	@Override
	public String toString() {
		return "GameScene [systems=" + systems + " children=" + childrens + "]@" + hashCode();
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<?> clazz) {
		for(final NecessarilySystems an : ClassUtil.getAllAnnotations(clazz, NecessarilySystems.class))
			for(final Class<? extends GameSystem> cl : an.value()) if(!systems.containsClass(cl))
				addSystem(GameSystem.createSystem(cl));
	}
	
	@Override
	public void update() {
		Logger.print("update scene", "s %d", System.nanoTime());
		systems.update();
//		for(final GameObject object : childrens) object.update();TODO
		Logger.print("update scene", "f %d", System.nanoTime());
	}
	
	@Override
	public void updateUpTreeComponents() {
		allTreeComponents.clear();
		for(final GameObject object : childrens) allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
	}

}
