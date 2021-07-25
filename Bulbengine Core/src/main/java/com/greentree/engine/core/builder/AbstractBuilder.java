package com.greentree.engine.core.builder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;
import com.greentree.engine.core.builder.context.SceneBuildContext;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {

	private final Stack<List<Pair<GameComponent, T>>> contextComponent = new Stack<>();
	private final Stack<List<Pair<GameSystem<?>, T>>> contextSystem = new Stack<>();

	public final static String getNameOfField(final Field field) {
		String xmlName = field.getAnnotation(EditorData.class).value();
		if(!xmlName.isBlank()) return xmlName;
		xmlName = field.getName();
		if(!xmlName.isBlank()) return xmlName;
		throw new IllegalArgumentException("could not find a suitable name of " + field);
	}

	protected final void addComponentToFill(final GameComponent component, final T in) {
		contextComponent.get(0).add(new Pair<>(component, in));
	}

	protected final void addSystemToFill(final GameSystem<?> system, final T in) {
		contextSystem.get(0).add(new Pair<>(system, in));
	}
	public final GameComponent createComponent(final Class<? extends GameComponent> clazz) {
		try {
			return ClassUtil.newInstance(clazz);
		}catch(final Exception e) {
			Log.warn("not create component " + clazz, e);
			return null;
		}
	}

	public final GameComponent createComponent(final InputStream in) {
		return createComponent(this.parse(in));
	}

	public final GameComponent createComponent(final T in) {
		return createComponent(this.getComponentClass(in));
	}

	public final GameObject createObject(final InputStream in, final GameObjectParent parent) {
		return this.createObject(this.parse(in), parent);
	}

	public final GameObject createObject(final T in, final GameObjectParent parent) {
		return new GameObject(this.getObjectName(in), parent);
	}
	@Override
	public final SceneBuildContext createScene(final InputStream in, GameScene parent) {
		return new SceneBuildContext(this.getSceneName(this.parse(in)), parent, in) {

			@Override
			public GameScene fill() {
				fillScene(scene, in);
				return getScene();
			}

		};
	}

	public final GameSystem<?> createSystem(final GameScene scene, final Class<? extends MultiBehaviour> cl) {
		return new GameSystem<>(scene, ClassUtil.newInstance(cl));
	}

	protected final GameSystem<?> createSystem(final GameScene scene, final T in) {
		try {
			return createSystem(scene, getMultiBehaviourClass(in));
		}catch(final Exception e) {
			Log.warn("system not create " + in, e);
			return null;
		}
	}

	public void fillComponent(final GameComponent component, final T in) {
		try {
			setFields(component, in);
		}catch(final Exception e) {
			Log.warn("not create component " + in, e);
		}
	}

	public final void fillObject(final GameObject object, final InputStream in) {
		this.fillObject(object, this.parse(in));
	}

	protected abstract void fillObject(GameObject node, T in);
	public void fillScene(GameScene scene, InputStream in) {
		fillScene(scene, parse(in));
	}

	protected abstract void fillScene(GameScene node, T in);

	public void fillSystem(final GameSystem<?> system, final T in) {
		try {
			setFields(system.getBehaviour(), in);
		}catch(final Exception e) {
			Log.warn("not create component " + in, e);
		}
	}

	protected abstract Class<? extends GameComponent> getComponentClass(T parse);

	public abstract Class<? extends MultiBehaviour> getMultiBehaviourClass(T in);

	protected abstract String getObjectName(T in);

	protected String getSceneName(T in) {
		return getObjectName(in);
	}

	public abstract Object load(Field field, T xmlValue, Object _default) throws Exception;

	public abstract T parse(InputStream in);

	protected final void popComponents() {
		for(final Pair<GameComponent, T> element : this.contextComponent.remove(0)) this.fillComponent(element.first, element.seconde);
	}

	protected final void popSystems() {
		for(final Pair<GameSystem<?>, T> element : this.contextSystem.remove(0)) this.fillSystem(element.first, element.seconde);
	}

	protected final void pushComponents() {
		contextComponent.add(0, new ArrayList<>());
	}

	protected final void pushSystems() {
		contextSystem.add(0, new ArrayList<>());
	}

	protected boolean required(Field field) {
		return field.getAnnotation(Required.class) != null;
	}

	protected abstract void setFields(final Object object, final T attributes);

	protected Object setValue(final Object obj, final Field field, final T tvalue) throws Exception {
		final boolean flag = field.canAccess(obj);
		field.setAccessible(true);
		Object _default = null;
		try {
			_default = field.get(obj);
		}catch(final IllegalArgumentException | IllegalAccessException e) {
			throw new NullPointerException(obj + " " + field + " " + tvalue);
		}finally {
			field.setAccessible(flag);
		}
		Object value = tvalue == null?_default : load(field, tvalue, _default);
		if(value != null) {
			field.setAccessible(true);
			try {
				field.set(obj, value);
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally {
				field.setAccessible(flag);
			}
		}
		return value;
	}
}
