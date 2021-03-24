package com.greentree.engine.editor.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.Log;
import com.greentree.common.collection.OneClassSet;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.engine.Game;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.editor.AbstractBuilder;
import com.greentree.engine.editor.xml.loaders.BooleanLoader;
import com.greentree.engine.editor.xml.loaders.ByteLoader;
import com.greentree.engine.editor.xml.loaders.CharLoader;
import com.greentree.engine.editor.xml.loaders.DoubleLoader;
import com.greentree.engine.editor.xml.loaders.EnumLoader;
import com.greentree.engine.editor.xml.loaders.FloatLoader;
import com.greentree.engine.editor.xml.loaders.GameComponentLoader;
import com.greentree.engine.editor.xml.loaders.IntegerLoader;
import com.greentree.engine.editor.xml.loaders.MeshLoader;
import com.greentree.engine.editor.xml.loaders.ShortLoader;
import com.greentree.engine.editor.xml.loaders.StaticFieldLoader;
import com.greentree.engine.editor.xml.loaders.StringLoader;
import com.greentree.engine.editor.xml.loaders.TextureLoader;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameObjectParent;
import com.greentree.engine.object.GameScene;
import com.greentree.engine.object.GameSystem;

/** @author Arseny Latyshev */
public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {
	
	@Deprecated
	private static final Map<Class<? extends Loader>, Integer> map = new HashMap<>();
	private final List<String> packages = new ArrayList<>(List.of("", Transform.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", ColliderComponent.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", GameSystem.class.getPackageName() + "."));
	private final OneClassSet<Loader> loaders = new OneClassSet<>(
			List.of(new FloatLoader(), new IntegerLoader(), new TextureLoader(), new StaticFieldLoader(), new StringLoader(),
					new MeshLoader(), new BooleanLoader(), new EnumLoader(), new GameComponentLoader(), new DoubleLoader(),
					new ShortLoader(), new ByteLoader(), new CharLoader()));
	
	public BasicXMlBuilder() {
	}
	
	/** @return can xmlName be the name of the parameter */
	private boolean checkXmlNameFormat(final String xmlName) {
		if(xmlName.equals("type")) return false;
		return xmlName.isEmpty() == false;
	}
	
	@Deprecated
	private final void count(final Class<? extends Loader> clazz) {
		Integer i = BasicXMlBuilder.map.remove(clazz);
		if(i == null) i = 0;
		i++;
		BasicXMlBuilder.map.put(clazz, i);
		final ArrayList<Entry<Class<? extends Loader>, Integer>> list = new ArrayList<>(BasicXMlBuilder.map.entrySet());
		list.sort((a, b)->b.getValue().compareTo(a.getValue()));
		list.forEach(a->Log.debug(String.format("%s(%d) ", a.getKey().getSimpleName(), a.getValue())));
		Log.debug("\n");
	}
	
	private void createChildrens(final GameObjectParent scene, final XMLElement in) {
		final List<Pair<GameObject, XMLElement>> bufer = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, scene);
			bufer.add(new Pair<>(сhildren, element));
			scene.addChildren(сhildren);
		}
		for(final Pair<GameObject, XMLElement> pair : bufer) this.fillObject(pair.first, pair.second);
	}
	
	@SuppressWarnings("unchecked")
	private GameComponent createComponent(final XMLElement xmlElement) {
		final Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) Game
				.loadClass(xmlElement.getAttribute("type"), this.packages);
		try {
			final GameComponent component = ClassUtil.newInstance(clazz);
			this.setFields(component, ClassUtil.getAllFieldsWithAnnotation(clazz, EditorData.class), xmlElement.getAttributes());
			return component;
		}catch(final Exception e) {
			Log.warn("not create component " + xmlElement.getAttribute("type"), e);
			return null;
		}
	}
	
	@Override
	public GameObject createPrefab(final String prefabPath, final GameObjectParent parent) {
		final InputStream in     = ResourceLoader.getResourceAsStream(prefabPath + ".object");
		final GameObject  object = this.createObject(in, parent);
		this.fillObject(object, in);
		object.initSratr();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	private GameSystem createSystem(final XMLElement xmlElement) {
		final Class<? extends GameSystem> clazz = (Class<? extends GameSystem>) Game.loadClass(xmlElement.getContent(),
				this.packages);
		try {
			return ClassUtil.newInstance(clazz);
		}catch(final Exception e) {
			Log.warn("not create component " + xmlElement.getAttribute("type"), e);
			return null;
		}
	}
	
	@Override
	protected void fillObject(final GameObject object, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = this.createComponent(element);
			if(component == null) continue;
			object.addComponent(component);
		}
		this.createChildrens(object, in);
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent() + ".");
	}
	
	@Override
	protected void fillScene(final GameScene scene, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		for(final XMLElement el : in.getChildrens("system")) {
			final GameSystem component = this.createSystem(el);
			if(component == null) continue;
			scene.addSystem(component);
		}
		this.createChildrens(scene, in);
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent() + ".");
	}
	
	@Override
	protected String getName(final XMLElement in) {
		return in.getAttribute("name", "name");
	}
	
	protected Object getValue(final String xmlValue, final Class<?> fieldClass) {
		Object result = null;
		for(final Loader loader : this.loaders) try {
			result = loader.load(fieldClass, xmlValue);
			if(result != null) {
				this.count(loader.getClass());
				return result;
			}
		}catch(final Exception e) {
		}
		return null;
	}
	
	private String getXmlName(final Field field) {
		String xmlName = field.getAnnotation(EditorData.class).name();
		if(this.checkXmlNameFormat(xmlName)) return xmlName;
		xmlName = field.getName();
		if(this.checkXmlNameFormat(xmlName)) return xmlName;
		throw new IllegalArgumentException("could not find a suitable name of " + field);
	}
	
	private String getXmlValue(final Field field, final Map<String, String> attributes) {
		String xmlValue = attributes.get(this.getXmlName(field));
		if(xmlValue == null) xmlValue = field.getAnnotation(EditorData.class).def();
		if(xmlValue == EditorData.NULL) throw new NullPointerException("return value is not indicated");
		return xmlValue;
	}
	
	@Override
	public XMLElement parse(final InputStream inputStream) {
		try {
			return new XMLElement(inputStream);
		}catch(final IOException e) {
			Log.warn(e);
			return null;
		}
	}
	
	private void setFields(final Object component, final List<Field> allEditorDataFields, final Map<String, String> attributes) {
		final Set<String> fieldNames = new HashSet<>(allEditorDataFields.size());
		for(final Field field : allEditorDataFields) fieldNames.add(this.getXmlName(field));
		for(final String attribute : attributes.keySet())
			if(attribute.equals("type") == false && fieldNames.contains(attribute) == false)
				Log.warn("xml element " + component + " has value " + attribute + " and it is not used. ");
		for(final Field field : allEditorDataFields) {
			String value = null;
			try {
				value = this.getXmlValue(field, attributes);
				this.setValue(component, field, value);
			}catch(final NullPointerException e) {
				if(value == null && field.getClass().isPrimitive()) continue;
				throw new RuntimeException(
						field + " has annotation " + EditorData.class.getSimpleName() + " and value not convert", e);
			}catch(final Exception e) {
			}
		}
	}
	
	protected void setValue(final Object obj, final Field f, final String xmlValue) throws NullPointerException {
		if(xmlValue.isEmpty() == false) {
			final boolean flag = f.canAccess(obj);
			f.setAccessible(true);
			final Object value = this.getValue(xmlValue, f.getType());
			try {
				f.set(obj, value);
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally {
				f.setAccessible(flag);
			}
			if(value == null) throw new NullPointerException(obj + "." + f.getName() + " is not initialize");
		}
	}
}
