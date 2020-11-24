package com.greentree.engine.object;

import static com.greentree.engine.object.GameObjectEvent.EventType.create;
import static com.greentree.engine.object.GameObjectEvent.EventType.destroy;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.greentree.engine.Debug;
import com.greentree.engine.Game;
import com.greentree.engine.Log;
import com.greentree.engine.Time;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.util.xml.XMLElement;
import com.greentree.util.xml.XMLParser;

public final class GameObject implements Serializable {

	private static final long serialVersionUID = 1L;
	final HashMap<Class<? extends GameComponent>, GameComponent> component;
	private transient boolean initialize;
	private final String name;
	private final List<String> tags;

	public GameObject(final String name) throws IllegalArgumentException {
		this(Game.getResurse(name), true);
	}
	
	GameObject(XMLElement in, boolean prefab) {
		if(!in.getAttribute("file").equals("")) {
			final String file = in.getAttribute("file");
			name = new File(file).getName().replaceFirst(".obj", "");
			in = XMLParser.parse(Game.getRoot(), file, "obj");
		}else {
			name = in.getAttribute("name");
		}
		//
		component = new HashMap<>();
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = GameComponent.createComponent(element);
			if(component == null) continue;
			this.component.put(component.getClass(), component);
		}
		//
		tags = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("tags"))
			for(final XMLElement element1 : element.getChildrens("tag")) tags.add(element1.getAttribute("name"));
		
		if(prefab) {
			Game.getCurrentScene().addObject(this);
		}
	}

	public void CollideEvent(final GameObject other) {
		for(final GameComponent gc : component.values()) gc.CollideEvent(other);
	}

	public void destroy() {
		Game.getEventSystem().eventNoQueue(new GameObjectEvent(destroy, this));
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
		Game.getEventSystem().event(new GameObjectEvent(create, this));
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
	}
}
