package com.greentree.engine.core.object;

import java.util.ArrayList;

import com.greentree.common.ClassUtil;
import com.greentree.engine.core.system.GroupSystem;
import com.greentree.engine.core.system.NecessarilySystems;

/** @author Arseny Latyshev */
public final class GameScene extends GameObjectParent {
	
	private final SystemCollection systems;
	
	public GameScene(final String name) {
		super(name);
		systems = new SystemCollection();
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
		for(final GameSystemGroup system : systems) system.initSratr();
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
		if(!systems.parallelStream().map(e -> e.isUpdated()).filter(e -> !e).findAny().isEmpty())return;
		for(final GameSystemGroup system : systems) system.update();
		for(final GameObject object : childrens) object.update();
	}
	
	@Override
	public void updateUpTreeComponents() {
		allTreeComponents.clear();
		for(final GameObject object : childrens) allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
	}
	
	/** @author Arseny Latyshev */
	public class SystemCollection extends ArrayList<GameSystemGroup> {
		private static final long serialVersionUID = 1L;

		public boolean contains(GameSystem o) {
			return null != parallelStream().filter(e -> e.contains(o)).findAny().orElse(null);
		}
		
		@SuppressWarnings("unchecked")
		public <S extends GameSystem> S get(Class<S> clazz) {
			return parallelStream().map(e -> (S)e.get(clazz)).filter(e -> e != null).findAny().orElse(null);
		}

		public boolean containsClass(Class<? extends GameSystem> cl) {		
			return null != parallelStream().filter(e -> e.containsClass(cl)).findAny().orElse(null);
		}

		public boolean add(GameSystem system) {
			if(contains(system))return false;
			GroupSystem group = system.getClass().getAnnotation(GroupSystem.class);
			GameSystemGroup g = getGroup((group == null) ? null : group.value());
			g.add(system);
			return true;
		}
		private int k;
		public GameSystemGroup getGroup(String string) {
			if(string == null) {
				GameSystemGroup group0 = new GameSystemGroup("default-"+(k++), true);
				add(group0);
				return group0;
			}
			if(string.isBlank())throw new UnsupportedOperationException("group name cannot be blank");
			GameSystemGroup group0 = parallelStream().filter(g -> string.equals(g.getName())).findAny().orElse(null);
			if(group0 == null) {
				if("graphics".equals(string))
					group0 = new GameSystemGroup(string, true);
				else
					group0 = new GameSystemGroup(string, true);
				add(group0);
			}
			return group0;
		}
		
	}

}
