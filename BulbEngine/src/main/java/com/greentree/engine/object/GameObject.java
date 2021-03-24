package com.greentree.engine.object;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.Log;
import com.greentree.common.collection.ClassTree;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.engine.Game;
import com.greentree.engine.component.ComponentEvent;
import com.greentree.engine.component.ComponentEvent.EventType;
import com.greentree.engine.component.RequireComponent;
import com.greentree.engine.corutine.Corutine;

public final class GameObject extends GameObjectParent {
	
	private final ClassTree<GameComponent> components;
	private GameObjectParent parent;
	private String name;
	private final Collection<Corutine> corutines;
	
	public GameObject(final String name, final GameObjectParent parent) {
		super("object name");
		this.components = new HashMapClassTree<>();
		this.corutines = new LinkedList<>();
		this.name = name;
		this.parent = parent;
		parent.addChildren(this);
	}
	
	public boolean addComponent(final GameComponent component) {
		if(this.components.add(component)) {
			this.updateUpTreeComponents();
			this.tryAddNecessarilySystem(component.getClass());
			return true;
		}
		return false;
	}
	
	public void destroy() {
		if(isDestroy()) {
//			throw new RuntimeException("destroy desroed object");
			return;
		}
		getParent().removeChildren(this);
		for(GameComponent component : components) {
			Game.event(new ComponentEvent(EventType.destroy, component));
		}
		updateUpTreeComponents();
		parent = null;
	}
	
	public <T extends GameComponent> T getComponent(final Class<T> clazz) {
		final Set<? extends T> list = this.components.get(clazz);
		if(list.isEmpty()) {
			Log.warn("Component " + clazz.getSimpleName() + " not create in Node " + this);
			return null;
		}
		return list.iterator().next();
	}
	
	public GameObjectParent getParent() {
		return this.parent;
	}
	
	public GameScene getScene() {
		if(this.getParent() instanceof GameScene) return (GameScene) this.parent;
		return ((GameObject) this.parent).getScene();
	}
	
	public boolean hasComponent(final Class<? extends GameComponent> clazz) {
		return this.components.containsClass(clazz);
	}
	
	public boolean hasComponent(final GameComponent component) {
		return this.components.contains(component);
	}
	
	public boolean isDestroy() {
		return getParent() == null || !getParent().contains(this);
	}
	
	@Override
	protected void start() {
		if(!Validator.checkRequireComponent(this.components)) Log.error(
				"component " + Validator.getBrokRequireComponentClass(this.components) + " require is not fulfilled \n" + this);
		for(GameComponent component : components)component.initAwake(this);
		for(GameComponent component : components)component.initSratr();
		for(GameObject component : childrens)component.start();
	}
	
	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder("[object(").append(this.getName()).append(")@").append(this.hashCode());
		if(!this.components.isEmpty()) {
			final StringBuilder r = new StringBuilder("\ncomponents:");
			for(final Object obj : this.components) r.append("\n").append(obj.toString());
			res.append(r.toString().replace("\n", "\n\t"));
		}
		return res.toString() + "]";
	}
	
	@Override
	public void tryAddNecessarilySystem(final Class<?> clazz) {
		this.getScene().tryAddNecessarilySystem(clazz);
	}
	
	@Override
	protected void update() {
		for(GameComponent component : components)component.update();
		for(GameObject component : childrens)component.update();
		this.corutines.removeIf(Corutine::run);
	}
	
	@Override
	public void updateUpTreeComponents() {
		this.allTreeComponents.clear();
		for(final GameObject object : this.childrens) this.allTreeComponents.addAll(object.getAllComponents(GameComponent.class));
		this.allTreeComponents.addAll(this.components);
		this.parent.updateUpTreeComponents();
	}

	public void startCorutine(Corutine corutine) {
		corutines.add(corutine);
	}

	public String getName() {
		return name;
	}
}

final class Validator {
	
	private Validator() {
	}
	
	public static boolean checkRequireComponent(final Iterable<GameComponent> components) {
		A : for(final Class<? extends GameComponent> requireClases : Validator.getRequireComponentClases(components)) {
			for(final Class<? extends GameComponent> clazz : ClassUtil.getClases(components))
				if(requireClases.isAssignableFrom(clazz)) continue A;
			return false;
		}
		return true;
	}
	
	public static Class<? extends GameComponent> getBrokRequireComponentClass(final Iterable<GameComponent> components) {
		final Set<Class<? extends GameComponent>> clases = ClassUtil.getClases(components);
		for(final Class<? extends GameComponent> clazz : Validator.getRequireComponentClases(components))
			if(!clases.contains(clazz)) return clazz;
		return null;
	}
	
	public static Set<Class<? extends GameComponent>> getRequireComponentClases(final Iterable<GameComponent> components) {
		final Set<Class<? extends GameComponent>> requireComponents = new HashSet<>();
		for(final GameComponent com : components)
			for(final RequireComponent rcom : ClassUtil.getAllAnnotations(com.getClass(), RequireComponent.class))
				Collections.addAll(requireComponents, rcom.value());
		return requireComponents;
	}
}
