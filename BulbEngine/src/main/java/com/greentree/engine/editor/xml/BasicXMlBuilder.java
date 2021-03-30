package com.greentree.engine.editor.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.Log;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.engine.Game;
import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.DefoultValue;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.editor.AbstractBuilder;
import com.greentree.engine.editor.LoaderList;
import com.greentree.engine.editor.loaders.BooleanLoader;
import com.greentree.engine.editor.loaders.ByteLoader;
import com.greentree.engine.editor.loaders.CharLoader;
import com.greentree.engine.editor.loaders.DoubleLoader;
import com.greentree.engine.editor.loaders.EnumLoader;
import com.greentree.engine.editor.loaders.FloatLoader;
import com.greentree.engine.editor.loaders.GameComponentLoader;
import com.greentree.engine.editor.loaders.IntegerLoader;
import com.greentree.engine.editor.loaders.MeshLoader;
import com.greentree.engine.editor.loaders.ShortLoader;
import com.greentree.engine.editor.loaders.StaticFieldLoader;
import com.greentree.engine.editor.loaders.StringLoader;
import com.greentree.engine.editor.loaders.TextureLoader;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameObjectParent;
import com.greentree.engine.object.GameScene;
import com.greentree.engine.object.GameSystem;

/** @author Arseny Latyshev */
public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {
	
	private final List<String> packages = new ArrayList<>(List.of("", Transform.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", CircleColliderComponent.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", GameSystem.class.getPackageName() + "."));
	
	private final LoaderList loaders = new LoaderList();
	
	public BasicXMlBuilder() {
		loaders.addLoader(new FloatLoader());
		loaders.addLoader(new IntegerLoader());
		loaders.addLoader(new TextureLoader());
		loaders.addLoader(new StaticFieldLoader());
		loaders.addLoader(new StringLoader());
		loaders.addLoader(new MeshLoader());
		loaders.addLoader(new BooleanLoader());
		loaders.addLoader(new EnumLoader());
		loaders.addLoader(new GameComponentLoader());
		loaders.addLoader(new DoubleLoader());
		loaders.addLoader(new ShortLoader());
		loaders.addLoader(new ByteLoader());
		loaders.addLoader(new CharLoader());
	}
	
	/** @return can xmlName be the name of the parameter */
	private boolean checkXmlNameFormat(final String xmlName) {
		if("type".equals(xmlName)) return false;
		return xmlName.isEmpty() == false;
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
		final Map<String, String>            map   = xmlElement.getAttributes();
		final Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) Game.loadClass(map.remove("type"),
				this.packages);
		try {
			final GameComponent component = ClassUtil.newInstance(clazz);
			this.setFields(component, map);
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
		//		final long startTime = System.nanoTime();//debug
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		for(final XMLElement el : in.getChildrens("system")) {
			final GameSystem component = this.createSystem(el);
			if(component == null) continue;
			scene.addSystem(component);
		}
		this.createChildrens(scene, in);
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent() + ".");
		//		Log.debug(String.format("fill scene %f", (System.nanoTime() - startTime) / 1_000_000_000d));//debug
	}
	
	@Override
	protected String getName(final XMLElement in) {
		return in.getAttribute("name", "name");
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
		return xmlValue;
	}
	private String getDefoultValue(final Field field) {
		final DefoultValue value = field.getAnnotation(DefoultValue.class);
		if(value == null)return null;
		return value.value();
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
	
	private void setFields(final Object component, final Map<String, String> attributes) {
		final List<Field> allEditorDataFields = ClassUtil.getAllFieldsWithAnnotation(component.getClass(), EditorData.class);
		final Set<String> xmlNamesToFind = new HashSet<>(allEditorDataFields.size());
		for(final Field field : allEditorDataFields) xmlNamesToFind.add(this.getXmlName(field));
		for(final String attribute : attributes.keySet())
			if(xmlNamesToFind.contains(attribute) == false)
				Log.warn("xml element " + component + " has value " + attribute + " and it is not used. ");//не часть алгоритма
		for(final Field field : allEditorDataFields) {
			String value =  this.getXmlValue(field, attributes);
			if(value == null)value = getDefoultValue(field);
			this.setValue(component, field, value);
		}
	}
	
	protected void setValue(final Object obj, final Field f, final String xmlValue) throws NullPointerException {
		final boolean flag = f.canAccess(obj);
		f.setAccessible(true);
		final Object value = loaders.load(f.getType(), xmlValue);
		if(value == null) {
			if(!f.getType().isPrimitive())
				throw new NullPointerException(obj + "." + f.getName() + " is not initialize");
		}else
			try {
				f.set(obj, value);
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally {
				f.setAccessible(flag);
			}
	}
}
