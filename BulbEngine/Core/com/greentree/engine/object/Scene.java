package com.greentree.engine.object;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.greentree.engine.Game;
import com.greentree.engine.collider.Collider;
import com.greentree.engine.collider.ColliderList;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.gui.ui.ButtonListenerManager;
import com.greentree.engine.input.KeyListenerManager;
import com.greentree.engine.input.MouseListenerManager;
import com.greentree.util.xml.XMLElement;
import com.greentree.util.xml.XMLParser;

public class Scene implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient final ColliderList colliders;
	private final EventSystem eventSystem;
	private final Set<GameObject> objects, _objects;
	private final Map<String, String> prefab;
	
	public Scene(final XMLElement in) {
		objects = new HashSet<>();
		_objects = new HashSet<>();
		prefab = new HashMap<>();
		for(final XMLElement e : in.getChildrens("object_prefab")) {
			final String file = e.getAttribute("file");
			prefab.put(new File(file).getName(), file);
		}
		eventSystem = new EventSystem();
		eventSystem.addListenerManager(new KeyListenerManager());
		eventSystem.addListenerManager(new MouseListenerManager());
		eventSystem.addListenerManager(new ButtonListenerManager());
		eventSystem.addListenerManager(new GameObjectListenerManager());
		colliders = new ColliderList(eventSystem);
		for(final XMLElement e : in.getChildrens("object")) new GameObject(e, this);
		eventSystem.addListener(new GameObjectListener() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void creating(final GameObject gameObject) {
				_objects.add(gameObject);
			}
			
			@Override
			public void destroy(final GameObject gameObject) {
				objects.remove(gameObject);
			}
		});
	}

	public void addCollider(final Collider collider, final GameObject obj) {
		colliders.addCollider(collider, obj);
	}
	
	public List<GameObject> findObjects(final Predicate<GameObject> filter) {
		final List<GameObject> list = new ArrayList<>(objects);
		list.removeIf(o->!filter.test(o));
		return list;
	}
	
	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public String getName(final GameObject gameObject) {
		return gameObject.getName();
	}

	public XMLElement getResurse(final String name) {
		return XMLParser.parse(Game.getRoot(), prefab.get(name), "obj");
	}
	
	public void update() {
		eventSystem.update();
		if(!_objects.isEmpty()) {
			objects.addAll(_objects);
			for(final GameObject obj : _objects) obj.init(this);
			_objects.clear();
		}
		colliders.update();
		for(final GameObject object : objects) object.update();
	}
}
