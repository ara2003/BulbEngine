package com.greentree.engine;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greentree.engine.component.util.ComponentList;
import com.greentree.engine.component.util.GameComponentListener;
import com.greentree.engine.component.util.GameComponentListenerManager;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.ListenerManager;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.util.ArrayUtil;
import com.greentree.util.ClassList;
import com.greentree.util.OneClassSet;

public class Scene implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final EventSystem eventSystem;
	private final List<GameObject> objects, objects_add;
	private final OneClassSet<GameSystem> systems, systems_add;
	private final ClassList<GameComponent> components;
	
	public Scene() {
		objects = new ArrayList<>();
		objects_add = new ArrayList<>();
		systems_add = new OneClassSet<>();
		systems = new OneClassSet<>();
		components = new ClassList<>();
		eventSystem = new EventSystem();
		
		eventSystem.addListenerManager(new GameObjectListenerManager());
		eventSystem.addListenerManager(new GameComponentListenerManager());
		
		eventSystem.addListener(new GameComponentListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void create(final GameComponent component) {
				components.add(component);
			}
			@Override
			public void destroy(final GameComponent component) {
				components.remove(component);
			}
		});
	}
	
	public void addObject(final GameObject obj) {
		objects_add.add(obj);
		obj.awake();
	}
	
	/**
	 * @param gameSystem
	 */
	public void addSystem(GameSystem gameSystem) {
	}
	
	public void destroy(final GameObject gameObject) {
		objects.remove(gameObject);
	}
	
	public List<GameObject> findObjectsHasComponent(Class<? extends GameComponent> clazz) {
		return ArrayUtil.findObjects(objects, obj->obj.hasComponent(clazz));
	}
	
	public List<GameObject> findObjectsWithTag(String tag) {
		return ArrayUtil.findObjects(objects, obj->obj.hasTag(tag));
	}
	
	public <T extends GameComponent> ComponentList<T> getComponents(final Class<T> c) {
		return new ComponentList<>(components.get(c));
	}
	
	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public String getName(final GameObject gameObject) {
		return gameObject.getName();
	}
	
	public <T extends GameSystem> T getSystem(Class<T> clazz) {
		return systems.get(clazz);
	}
	
	public void tryAddNecessarily(final Class<?> clazz) {
		final List<necessarily> necessarilys = ClassUtil.getAnnotations(clazz, necessarily.class);
		for(necessarily necessarily : necessarilys) {
			for(final Class<?> c : necessarily.value()) {
				if(GameSystem.class.isAssignableFrom(c)) {
					systems_add.add(GameSystem.createSystem(c));
				}else if(ListenerManager.class.isAssignableFrom(c)) try {
					final ListenerManager listenerManager = (ListenerManager) c.getConstructor().newInstance();
					eventSystem.addListenerManager(listenerManager);
				}catch(InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					Log.error(e);
				}else Log.warn(
						clazz + " use " + necessarily.class.getSimpleName() + " class can not processing " + c.getName());
			}
		}
	}
	
	public void update() {
		eventSystem.update();
		if(!objects_add.isEmpty()) {
			final Set<GameObject> sub = new HashSet<>(objects_add);
			objects_add.clear();
			sub.forEach(GameObject::start);
			objects.addAll(sub);
		}
		if(!systems_add.isEmpty()) {
			final Set<GameSystem> sub = new HashSet<>(systems_add);
			systems_add.clear();
			sub.forEach(GameSystem::init);
			systems.addAll(sub);
		}
		for(final GameSystem system : systems) system.execute();
		for(final GameObject object : objects) object.update();
	}
}
