package com.greentree.engine.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.gui.ui.ButtonListenerManager;
import com.greentree.engine.input.KeyListenerManager;
import com.greentree.engine.input.MouseListenerManager;
import com.greentree.engine.system.util.ISystem;
import com.greentree.util.xml.XMLElement;

public class Scene implements Serializable {

	private static final long serialVersionUID = 1L;
	private final EventSystem eventSystem;
	private final Set<GameObject> objects, objects_add;
	private final Set<ISystem> systems;
	private final Map<String, XMLElement> prefab;
	
	public Scene(final XMLElement in) {
		objects = new HashSet<>();
		objects_add = new HashSet<>();
		systems = new HashSet<>();
		prefab = new HashMap<>();
		for(final XMLElement e : in.getChildrens("object_prefab")) {
			String file = e.getAttribute("file").replace("/", "\\");
			file = (String) file.subSequence(file.lastIndexOf('\\')+1, file.length());
			prefab.put(file, e);
		}
		//
		eventSystem = new EventSystem();
		eventSystem.addListenerManager(new KeyListenerManager());
		eventSystem.addListenerManager(new MouseListenerManager());
		eventSystem.addListenerManager(new ButtonListenerManager());
		eventSystem.addListenerManager(new GameObjectListenerManager());
		//
		eventSystem.addListener(new GameObjectListener() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void create(final GameObject gameObject) {
				objects.add(gameObject);
			}
			
			@Override
			public void destroy(final GameObject gameObject) {
				objects.remove(gameObject);
			}
		});
		for(final XMLElement e : in.getChildrens("object")) objects_add.add(new GameObject(e, false));
	}
	
	public void addObject(final GameObject obj) {
		objects_add.add(obj);
	}
	
	public void addSystem(final ISystem system) {
		systems.add(system);
	}

	public List<GameObject> findObjects(final Predicate<GameObject> filter) {
		final List<GameObject> list = new ArrayList<>(objects);
		list.removeIf(o->!filter.test(o));
		return list;
	}
	
	private List<GameComponent> getAllComponent() {
		final List<GameComponent> list = new ArrayList<>();
		objects.forEach(a->list.addAll(a.component.values()));
		return list;
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
	
	public void update() {
		eventSystem.update();
		if(!objects_add.isEmpty()) {
			Set<GameObject> sub = new HashSet<>(objects_add);
			objects_add.clear();
			sub.forEach(obj->obj.init());
		}
		for(final ISystem system : systems) system.execute(getAllComponent());
		for(final GameObject object : objects) object.update();
	}
}
