package com.greentree.engine.object;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.GameComponentListener;
import com.greentree.engine.component.util.GameComponentListenerManager;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.event.ListenerManager;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.util.ClassList;
import com.greentree.util.OneClassSet;
import com.greentree.xml.XMLElement;

public class Scene implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final EventSystem eventSystem;
	private final List<GameObject> objects, objects_add;
	private final OneClassSet<GameSystem> systems, systems_add;
	private final ClassList<GameComponent> components;
	private final Map<String, XMLElement> prefab;
	
	public Scene() {
		objects = new ArrayList<>();
		objects_add = new ArrayList<>();
		systems_add = new OneClassSet<>();
		systems = new OneClassSet<>();
		prefab = new HashMap<>();
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
		obj.init();
	}
	
	public void destroy(final GameObject gameObject) {
		objects.remove(gameObject);
	}
	
	@Deprecated
	public List<GameObject> findObjects(final Predicate<GameObject> filter) {
		final List<GameObject> list = new ArrayList<>(objects);
		list.removeIf(o->!filter.test(o));
		return list;
	}
	
	public List<GameObject> findObjectsHasComponent(Class<? extends GameComponent> clazz) {
		final List<GameObject> list = new ArrayList<>();
		for(GameObject obj : objects)if(obj.hasComponent(clazz))list.add(obj);
		return list;
	}
	
	public List<GameObject> findObjectsWithTag(String tag) {
		final List<GameObject> list = new ArrayList<>();
		for(GameObject obj : objects)if(obj.hasTag(tag))list.add(obj);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> List<T> getComponents(final Class<T> c) {
		return (List<T>) components.get(c);
	}
	
	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public String getName(final GameObject gameObject) {
		return gameObject.getName();
	}
	
	public XMLElement getResurse(final String name) {
		return prefab.get(name);
	}
	
	public void start(final XMLElement in) {
		for(final XMLElement e : in.getChildrens("object_prefab")) {
			String file = e.getAttribute("file").replace("/", "\\");
			file = (String) file.subSequence(file.lastIndexOf('\\') + 1, file.length());
			prefab.put(file, e);
		}
		for(final XMLElement system : in.getChildrens("systems"))
			for(final XMLElement e : system.getChildrens("system"))
				systems_add.add(GameSystem.createSystem(Game.loadClass(e.getAttribute("name"))));
		for(final XMLElement e : in.getChildrens("object")) new GameObject(e);
		if(!systems_add.isEmpty()) {
			final Set<GameSystem> sub = new HashSet<>(systems_add);
			systems_add.clear();
			sub.forEach(GameSystem::init);
			systems.addAll(sub);
		}
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
			//			sub.forEach(GameObject::init);
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
