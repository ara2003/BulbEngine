package com.greentree.engine.core.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.greentree.common.ClassUtil;
import com.greentree.common.pair.Pair;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.component.NewComponentEvent;
import com.greentree.engine.core.layer.Layer;
import com.greentree.engine.core.layer.LayerFactory;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {

	private static final LayerFactory layerFactory = new LayerFactory();
	private final List<Pair<GameComponent, T>> contextComponent = new ArrayList<>();
	private final List<Pair<GameSystem, T>> contextSystem = new ArrayList<>();

	@Override
	public final GameComponent createComponent(final InputStream in) {
		return this.createComponent(this.parse(in));
	}

	protected abstract GameComponent createComponent(T parse);

	@Override
	public final GameObject createObject(final InputStream in, final GameObjectParent parent) {
		return this.createObject(this.parse(in), parent);
	}

	public final GameObject createObject(final T in, final GameObjectParent parent) {
		return new GameObject(this.getName(in), getLayer(this.getLayerName(in)), parent);
	}

	@Override
	public final GameScene createScene(final InputStream in) {
		return new GameScene(this.getName(this.parse(in)));
	}

	@Override
	public final GameSystem createSystem(final Class<? extends GameSystem> cl) {
		return ClassUtil.newInstance(cl);
	}

	@Override
	public final void fillComponent(final GameComponent component, final InputStream in) {
		this.fillComponent(component, this.parse(in));
	}

	protected abstract void fillComponent(GameComponent component, T parse);

	@Override
	public final void fillObject(final GameObject object, final InputStream in) {
		this.fillObject(object, this.parse(in));
	}

	protected abstract void fillObject(GameObject node, T in);

	@Override
	public final void fillScene(final GameScene scene, final InputStream in) {
		this.fillScene(scene, this.parse(in));
	}

	protected abstract void fillScene(GameScene node, T in);

	@Override
	public final void fillSystem(final GameSystem system, final InputStream in) {
		fillSystem(system, parse(in));
	}

	protected abstract void fillSystem(GameSystem system, T in);

	@Override
	public final Layer getLayer(final String name) {
		return AbstractBuilder.layerFactory.get(name);
	}

	public final Layer getLayer(final T t) {
		return getLayer(getLayerName(t));
	}

	protected abstract String getLayerName(T object);

	protected abstract String getName(T in);

	public abstract T parse(InputStream in);

	protected final void popComponents() {
		for(final Pair<GameComponent, T> element : this.contextComponent) {
			this.fillComponent(element.first, element.second);
			Events.getEventsystem().event(new NewComponentEvent(element.first));
		}
		this.contextComponent.clear();
	}

	protected final void popSystems() {
		for(final Pair<GameSystem, T> element : this.contextSystem) this.fillSystem(element.first, element.second);
		this.contextSystem.clear();
	}

	protected final void pushComponent(final GameComponent component, final T in) {
		contextComponent.add(new Pair<>(component, in));
	}

	protected final void pushSystem(final GameSystem system, final T in) {
		contextSystem.add(new Pair<>(system, in));
	}
}
