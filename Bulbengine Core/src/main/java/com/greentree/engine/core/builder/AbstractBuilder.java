package com.greentree.engine.core.builder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public abstract class AbstractBuilder<T> implements Builder {

	private final List<Pair<GameComponent, T>> contextComponent = new ArrayList<>();
	private final List<Pair<GameSystem, T>> contextSystem = new ArrayList<>();

	public final static String getNameOfField(final Field field) {
		String xmlName = field.getAnnotation(EditorData.class).name();
		if(!xmlName.isBlank()) return xmlName;
		xmlName = field.getName();
		if(!xmlName.isBlank()) return xmlName;
		throw new IllegalArgumentException("could not find a suitable name of " + field);
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
	public final GameScene createScene(final InputStream in) {
		return new GameScene(this.getSceneName(this.parse(in)));
	}

	public final GameSystem createSystem(final Class<? extends MultiBehaviour> cl) {
		return new GameSystem(ClassUtil.newInstance(cl));
	}

	protected final GameSystem createSystem(final T in) {
		try {
			return createSystem(getMultiBehaviourClass(in));
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

	@Override
	public final void fillScene(final GameScene scene, final InputStream in) {
		this.fillScene(scene, this.parse(in));
	}
	protected abstract void fillScene(GameScene node, T in);

	protected void fillSystem(final GameSystem system, final T in) {
		try {
			setFields(system.getBehaviour(), in);
		}catch(final Exception e) {
			Log.warn("not create component " + in, e);
		}
	}


	protected abstract Class<? extends GameComponent> getComponentClass(T parse);

	protected abstract String getObjectName(T in);

	protected String getSceneName(T in) {
		return getObjectName(in);
	}

	public abstract Class<? extends MultiBehaviour> getMultiBehaviourClass(T in);

	public abstract Object load(Field field, T xmlValue) throws Exception;

	public abstract T parse(InputStream in);
	protected final void popComponents() {
		for(final Pair<GameComponent, T> element : this.contextComponent) this.fillComponent(element.first, element.second);
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

	protected boolean required(Field field) {
		boolean a =  field.getAnnotation(EditorData.class).required();
		boolean b =  field.getAnnotation(Required.class) != null;
		if(a)Log.warn("use Deprecated EditorData.required in field " + field);
		return a || b;
	}

	protected abstract void setFields(final Object object, final T attributes);

	protected Object setValue(final Object obj, final Field field, final T xmlValue) throws Exception {
		final boolean flag = field.canAccess(obj);
		field.setAccessible(true);
		Object value = null;
		if(xmlValue == null) try {
			value = field.get(obj);
		}catch(final IllegalArgumentException | IllegalAccessException e1) {
			throw new NullPointerException(obj + " " + field + " " + xmlValue);
		}finally {
			field.setAccessible(flag);
		}
		else value = load(field, xmlValue);
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
