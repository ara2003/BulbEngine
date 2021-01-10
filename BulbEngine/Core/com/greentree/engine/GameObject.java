package com.greentree.engine;

import static com.greentree.engine.GameObjectEvent.EventType.create;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.greentree.engine.component.util.GameComponentEvent;
import com.greentree.engine.corutine.Corutine;
import com.greentree.util.ArrayUtil;
import com.greentree.util.ClassList;

public final class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ClassList<GameComponent> components;
	private final List<GameObject> objects;
	private transient boolean start, awake;
	private final String name;
	private final Set<String> tags;
	private final List<Corutine> corutines;
	private final Timer timer = new Timer();
	
	public GameObject(String name) {
		components = new ClassList<>();
		objects = new ArrayList<>(0);
		tags = new HashSet<>();
		corutines = new LinkedList<>();
		this.name = name;
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
	
	public <T extends GameComponent> List<T> getComponentList(final Class<T> clazz) {
		return (List<T>) components.get(Objects.requireNonNull(clazz));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameComponent> List<T> getListComponent(final Class<T> clazz) {
		Objects.requireNonNull(clazz);
		final List<T> list = new ArrayList<>();
		for(final GameComponent component : components.get(GameComponent.class))
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

	void start() {
		if(start) {
			Log.error("second start object " + this, new Exception());
			return;
		}
		start = true;
		for(final GameComponent gc : components)gc.start(this);
	}

	void awake() {
		if(awake) {
			Log.error("second awake object " + this, new Exception());
			return;
		}
		awake = true;
		for(final GameComponent gc : components)gc.awake(this);
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

	public GameObject getIncludeObjectWithName(String name) {
		List<GameObject> list = ArrayUtil.findObjects(objects, obj->name.equals(obj.getName()));
		if(list.isEmpty()) {
			Log.warn(this + " don\'t include object with name " + name);
			return null;
		}
		return list.get(0);
	}

	public void addComponent(GameComponent component) {
		components.add(component);
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}
	
}
