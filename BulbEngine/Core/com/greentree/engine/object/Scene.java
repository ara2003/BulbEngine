package com.greentree.engine.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.greentree.engine.Game;
import com.greentree.engine.component.util.GameComponentListener;
import com.greentree.engine.component.util.GameComponentListenerManager;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.gui.ui.ButtonListenerManager;
import com.greentree.engine.input.KeyListenerManager;
import com.greentree.engine.input.MouseListenerManager;
import com.greentree.engine.loading.LoaderUtil;
import com.greentree.engine.phisic.ColliderListenerManager;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.util.xml.XMLElement;

public class Scene implements Serializable {

	private static final long serialVersionUID = 1L;
	private final EventSystem eventSystem;
	private final List<GameObject> objects, objects_add;
	private final List<GameSystem> systems, systems_add;
	private final Map<Class<? extends GameComponent>, Set<GameComponent>> components;
	private final Map<String, XMLElement> prefab;
	
	public Scene() {
		objects = new ArrayList<>();
		objects_add = new ArrayList<>();
		systems_add = new ArrayList<>();
		systems = new ArrayList<>();
		prefab = new HashMap<>();
		components = new HashMap<>();
		eventSystem = new EventSystem();
		//
		eventSystem.addListenerManager(new KeyListenerManager());
		eventSystem.addListenerManager(new MouseListenerManager());
		eventSystem.addListenerManager(new ButtonListenerManager());
		eventSystem.addListenerManager(new GameObjectListenerManager());
		eventSystem.addListenerManager(new GameComponentListenerManager());
		eventSystem.addListenerManager(new ColliderListenerManager());
		eventSystem.addListener(new GameComponentListener() {

			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			@Override
			public void create(final GameComponent component) {
				for(final Class<?> c : LoaderUtil.getAllPerant(component.getClass())) {
					Set<GameComponent> set = getComponents().get(c);
					if(set == null) set = new HashSet<>();
					set.add(component);
					getComponents().put((Class<? extends GameComponent>) c, set);
				}
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void destroy(final GameComponent component) {
				for(final Class<?> c : LoaderUtil.getAllPerant(component.getClass())) {
					Set<GameComponent> set = getComponents().get(c);
					if(set == null) set = new HashSet<>();
					set.remove(component);
					components.put((Class<? extends GameComponent>) c, set);
				}
			}
		});
	}
	
	public void addObject(final GameObject obj) {
		objects_add.add(obj);
	}

	public void addSystem(final Class<?> systemClass) {
		for(final GameSystem s : systems) if(s.getClass().equals(systemClass)) return;
		systems_add.add(GameSystem.createSystem(systemClass));
	}
	
	public void destroy(final GameObject gameObject) {
		objects.remove(gameObject);
	}

	public List<GameObject> findObjects(final Predicate<GameObject> filter) {
		final List<GameObject> list = new ArrayList<>(objects);
		list.removeIf(o->!filter.test(o));
		return list;
	}

	public Map<Class<? extends GameComponent>, Set<GameComponent>> getComponents() {
		return components;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> Set<T> getComponents(final Class<T> c) {
		if(components.get(c) == null) components.put(c, new HashSet<>());
		return (Set<T>) components.get(c);
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

	public boolean hasSystem(final Class<ColliderSystem> clazz) {
		for(final GameSystem s : systems) if(clazz.equals(s.getClass())) return true;
		return false;
	}
	
	public void start(final XMLElement in) {
		for(final XMLElement e : in.getChildrens("object_prefab")) {
			String file = e.getAttribute("file").replace("/", "\\");
			file = (String) file.subSequence(file.lastIndexOf('\\') + 1, file.length());
			prefab.put(file, e);
		}
		for(final XMLElement system : in.getChildrens("systems"))
			for(final XMLElement e : system.getChildrens("system")) addSystem(Game.loadClass(e.getAttribute("name")));
		for(final XMLElement e : in.getChildrens("object")) new GameObject(e);
		if(!objects_add.isEmpty()) {
			final Set<GameObject> sub = new HashSet<>(objects_add);
			objects_add.clear();
			sub.forEach(GameObject::init);
			objects.addAll(sub);
		}
		if(!systems_add.isEmpty()) {
			final Set<GameSystem> sub = new HashSet<>(systems_add);
			systems_add.clear();
			sub.forEach(GameSystem::init);
			systems.addAll(sub);
		}
	}

	public void update() {
		eventSystem.update();
		if(!objects_add.isEmpty()) {
			final Set<GameObject> sub = new HashSet<>(objects_add);
			objects_add.clear();
			sub.forEach(GameObject::init);
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
