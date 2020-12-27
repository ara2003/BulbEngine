package com.greentree.engine.object;

import static com.greentree.engine.object.GameObjectEvent.EventType.create;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.greentree.engine.Debug;
import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.Timer;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.corutine.Corutine;
import com.greentree.util.ClassList;
import com.greentree.xml.XMLElement;
import com.greentree.xml.XMLParser;

public final class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ClassList<GameComponent> components;
	private transient boolean initialize;
	private final String name;
	private final Set<String> tags;
	private final List<Corutine> corutines;
	private final Timer timer = new Timer();
	
	
	public GameObject(final String name) throws IllegalArgumentException {
		this(Game.getResurse(name));
	}
	
	GameObject(XMLElement in) {
		if(!in.getAttribute("file").equals("")) {
			final String file = in.getAttribute("file");
			name = new File(file).getName().replaceFirst(".obj", "");
			in = XMLParser.parse(Game.getAssets(), file, "obj");
		}else {
			name = in.getAttribute("name");
		}
		corutines = new LinkedList<>();
		components = new ClassList<>();
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = GameComponent.createComponent(element);
			if(component == null)continue;
			components.add(component);
		}
		tags = new HashSet<>();
		for(final XMLElement element : in.getChildrens("tags")) {
			for(final XMLElement element1 : element.getChildrens("tag")) {
				tags.add(element1.getAttribute("name"));
			}
		}
		Game.getCurrentScene().addObject(this);
		Game.eventNoQueue(new GameObjectEvent(create, this));
	}
	
	public void destroy() {
		Game.getCurrentScene().destroy(this);
		Game.eventNoQueue(new GameObjectEvent(GameObjectEvent.EventType.destroy, this));
		for(final GameComponent c : components)
			Game.eventNoQueue(new GameComponentEvent(GameComponentEvent.EventType.destroy, c));
		
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final T t = getComponentList(clazz).get(0);
		if(t == null)Log.warn("Component " + clazz.getSimpleName() + " not create in Object " + this,
				new NullPointerException());
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> List<T> getComponentList(final Class<T> clazz) {
		return (List<T>) components.get(Objects.requireNonNull(clazz));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> List<T> getListComponent(final Class<T> clazz) {
		Objects.requireNonNull(clazz);
		final List<T> list = new ArrayList<>();
		for(final GameComponent component : components)
			if(component.getClass().isAssignableFrom(clazz)) {
				list.add((T) component);
			}
		return list;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasComponent(Class<?> clazz) {
		return components.containsClass(clazz);
	}
	
	public boolean hasComponent(GameComponent component) {
		return components.contains(component);
	}
	
	public boolean hasTag(final String tag) {
		return tags.contains(tag);
	}
	
	void init() {
		if(initialize) {
			Log.error("second init object " + this, new Exception());
			return;
		}
		initialize = true;
		for(final GameComponent gc : components)
			gc.start(this);
	}
	
	public void startCorutine(final Corutine corutine) {
		corutines.add(corutine);
	}
	
	@Override
	public String toString() {
		return "[object " + name + "]";
	}
	public void update() {
		for(final GameComponent c : components) {
			timer.start(0);
			c.update();
			Debug.addTime(c.getClass().getSimpleName(), "" + timer.finish(0));
		}
		for(Corutine corutine : corutines)corutine.run();
	}
	
}
