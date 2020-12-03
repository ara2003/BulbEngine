package com.greentree.engine.object;

import static com.greentree.engine.object.GameObjectEvent.EventType.create;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.greentree.engine.Debug;
import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.Time;
import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.corutine.Corutine;
import com.greentree.util.xml.XMLElement;
import com.greentree.util.xml.XMLParser;

public final class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	final HashMap<Class<? extends GameComponent>, GameComponent> component;
	private transient boolean initialize;
	private final String name;
	private final Set<String> tags;
	private final Corutine mainCorutine;
	
	public GameObject(final String name) throws IllegalArgumentException {
		this(Game.getResurse(name));
	}
	
	GameObject(XMLElement in) {
		mainCorutine = new Corutine(()->{}, null);
		if(!in.getAttribute("file").equals("")) {
			final String file = in.getAttribute("file");
			name = new File(file).getName().replaceFirst(".obj", "");
			in = XMLParser.parse(Game.getRoot(), file, "obj");
		}else name = in.getAttribute("name");
		//
		component = new HashMap<>();
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = GameComponent.createComponent(element);
			if(component == null) continue;
			this.component.put(component.getClass(), component);
		}
		//
		tags = new HashSet<>();
		for(final XMLElement element : in.getChildrens("tags"))
			for(final XMLElement element1 : element.getChildrens("tag")) tags.add(element1.getAttribute("name"));
		Game.getCurrentScene().addObject(this);
		Game.getEventSystem().eventNoQueue(new GameObjectEvent(create, this));
	}
	
	public void CollideEvent(final GameObject other) {
		for(final GameComponent gc : component.values()) gc.CollideEvent(other);
	}
	
	public void destroy() {
		Game.getCurrentScene().destroy(this);
		Game.getEventSystem().eventNoQueue(new GameObjectEvent(GameObjectEvent.EventType.destroy, this));
		for(final GameComponent c : component.values())
			Game.getEventSystem().eventNoQueue(new GameComponentEvent(GameComponentEvent.EventType.destroy, c));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		if(clazz == null) {
			Log.error("Component class don\'t null");
			return null;
		}
		final T a = (T) component.get(clazz);
		if(a == null) {
			Log.error("Component " + clazz.getSimpleName() + " not create in Object " + this,
					new NullPointerException());
			return null;
		}
		return a;
	}
	
	public String getName() {
		return name;
	}

	public boolean hasTag(final String tag) {
		return tags.contains(tag);
	}
	
	void init() {
		if(initialize) {
			Log.error("second init object " + this + " from " + Thread.currentThread().getStackTrace()[2]);
			return;
		}
		initialize = true;
		for(final GameComponent gc : component.values()) gc.start(this);
	}
	
	public void startCorutine(final Corutine corutine) {
		mainCorutine.addCorutine(corutine);
	}

	@Override
	public String toString() {
		return "[object " + name + "]";
	}

	public void update() {
		for(final GameComponent c : component.values()) {
			Time.start(0);
			c.update();
			Debug.addTime(c.getClass().getSimpleName(), "" + Time.finish(0));
		}
		mainCorutine.run();
	}
}
