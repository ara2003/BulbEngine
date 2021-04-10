package com.greentree.engine.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.engine.builder.loaders.BooleanLoader;
import com.greentree.engine.builder.loaders.ByteLoader;
import com.greentree.engine.builder.loaders.CharLoader;
import com.greentree.engine.builder.loaders.DoubleLoader;
import com.greentree.engine.builder.loaders.EnumLoader;
import com.greentree.engine.builder.loaders.FloatLoader;
import com.greentree.engine.builder.loaders.GameComponentLoader;
import com.greentree.engine.builder.loaders.IntegerLoader;
import com.greentree.engine.builder.loaders.Loader;
import com.greentree.engine.builder.loaders.LoaderList;
import com.greentree.engine.builder.loaders.ObjMeshLoader;
import com.greentree.engine.builder.loaders.ShaderProgramLoader;
import com.greentree.engine.builder.loaders.ShortLoader;
import com.greentree.engine.builder.loaders.StaticFieldLoader;
import com.greentree.engine.builder.loaders.StringLoader;
import com.greentree.engine.builder.loaders.TextureLoader;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.GameObject;
import com.greentree.engine.core.GameObjectParent;
import com.greentree.engine.core.GameScene;
import com.greentree.engine.core.GameSystem;
import com.greentree.engine.core.builder.AbstractBuilder;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;

/** @author Arseny Latyshev */
public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {
	
	private final Collection<String> packages;
	private final LoaderList loaders = new LoaderList();
	final List<Pair<GameComponent, XMLElement>> contextComponent = new ArrayList<>();
	private final Map<String, GameObject> hashPrefabs = new HashMap<>();
	
	public BasicXMlBuilder(final String... packages) {
		this.packages = new ArrayList<>(packages.length);
		Collections.addAll(this.packages, packages);
		
		this.loaders.addLoader(new FloatLoader());
		this.loaders.addLoader(new IntegerLoader());
		this.loaders.addLoader(new TextureLoader());
		this.loaders.addLoader(new StaticFieldLoader());
		this.loaders.addLoader(new StringLoader());
		this.loaders.addLoader(new ObjMeshLoader());
		this.loaders.addLoader(new BooleanLoader());
		this.loaders.addLoader(new EnumLoader());
		this.loaders.addLoader(new GameComponentLoader());
		this.loaders.addLoader(new DoubleLoader());
		this.loaders.addLoader(new ShortLoader());
		this.loaders.addLoader(new ByteLoader());
		this.loaders.addLoader(new CharLoader());
		this.loaders.addLoader(new ShaderProgramLoader());
	}
	
	private void addNecessarilyLoaders(final NecessarilyLoaders annotation) {
		if(annotation == null) return;
		for(final Class<? extends Loader> cl : annotation.value()) if(!this.loaders.hasLoader(cl)) {
			final Loader l = ClassUtil.newInstance(cl);
			this.loaders.addLoader(l);
		}
	}
	
	
	/** @return can xmlName be the name of the parameter */
	private boolean checkXmlNameFormat(final String xmlName) {
		if("type".equals(xmlName)) return false;
		return xmlName.isEmpty() == false;
	}
	
	@Override
	public GameComponent createComponent(final Class<? extends GameComponent> clazz) {
		try {
			final GameComponent component = ClassUtil.newInstance(clazz);
			return component;
		}catch(final Exception e) {
			Log.warn("not create component " + clazz, e);
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public GameComponent createComponent(final XMLElement xmlElement) {
		final String type = xmlElement.getAttribute("type");
		if(type.isBlank()) throw new IllegalArgumentException(xmlElement + " does not contain atribute \"type\"");
		final Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) ClassUtil.loadClass(type,
			this.packages);
		return this.createComponent(clazz);
	}
	
	@Override
	public GameObject createPrefab(final String prefabPath, final GameObjectParent parent) {
		//		final GameObject a = this.hashPrefabs.get(prefabPath);
		//		if(a == null) {
		final InputStream in     = ResourceLoader.getResourceAsStream(prefabPath + ".object");
		final GameObject  object = this.createObject(in, parent);
		this.fillObject(object, in);
		this.fillComponents();
		object.initSratr();
		this.hashPrefabs.put(prefabPath, object);
		return object;
		//		}else {
		//			if(a.isDestroy()) {
		//				this.hashPrefabs.remove(a);
		//				return this.createPrefab(prefabPath, parent);
		//			}
		//			return a.clone();
		//		}
	}
	
	@SuppressWarnings("unchecked")
	private GameSystem createSystem(final XMLElement xmlElement) {
		final Class<? extends GameSystem> clazz = (Class<? extends GameSystem>) ClassUtil.loadClass(xmlElement.getContent(),
			this.packages);
		try {
			return ClassUtil.newInstance(clazz);
		}catch(final Exception e) {
			Log.warn("not create component " + xmlElement.getAttribute("type"), e);
			return null;
		}
	}
	
	@Override
	public void fillComponent(final GameComponent component, final XMLElement in) {
		final Map<String, String> map = in.getAttributes();
		map.remove("type");
		try {
			this.setFields(component, map);
		}catch(final Exception e) {
			Log.warn("not create component " + in.getAttribute("type"), e);
		}
	}
	
	private void fillComponents() {
		for(final Pair<GameComponent, XMLElement> element : this.contextComponent) this.fillComponent(element.first, element.second);
		this.contextComponent.clear();
	}
	
	@Override
	protected void fillObject(final GameObject object, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		
		final List<Pair<GameObject, XMLElement>> contextObject = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, object);
			contextObject.add(new Pair<>(сhildren, element));
			object.addChildren(сhildren);
		}
		
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = this.createComponent(element);
			if(component == null) continue;
			this.contextComponent.add(new Pair<>(component, element));
			object.addComponent(component);
		}
		
		for(final Pair<GameObject, XMLElement> element : contextObject) this.fillObject(element.first, element.second);
		
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent() + ".");
	}
	
	@Override
	protected void fillScene(final GameScene scene, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent());
		
		for(final XMLElement el : in.getChildrens("system")) {
			final GameSystem component = this.createSystem(el);
			if(component == null) continue;
			scene.addSystem(component);
		}
		
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, scene);
			if(сhildren == null) continue;
			this.fillObject(сhildren, element);
			scene.addChildren(сhildren);
		}
		
		this.fillComponents();
		
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent());
	}
	
	private String getDefoultValue(final Field field) {
		final DefoultValue value = field.getAnnotation(DefoultValue.class);
		if(value == null) return null;
		return value.value();
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
		final String xmlValue = attributes.get(this.getXmlName(field));
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
	
	private void setFields(final Object component, final Map<String, String> attributes) {
		final List<Field> allEditorDataFields = ClassUtil.getAllFieldsWithAnnotation(component.getClass(), EditorData.class);
		{
			final Set<String> xmlNamesToFind = new HashSet<>(allEditorDataFields.size());
			for(final Field field : allEditorDataFields) xmlNamesToFind.add(this.getXmlName(field));
			for(final String attribute : attributes.keySet())
				if(!xmlNamesToFind.contains(attribute))
					Log.warn("xml element " + component + " has value " + attribute + " and it is not used. [xmlNamesToFind=" + xmlNamesToFind + " attributes=" + attributes + "]");//не часть алгоритма
		}
		for(final Field field : allEditorDataFields) {
			String value = this.getXmlValue(field, attributes);
			if(value == null) value = this.getDefoultValue(field);
			this.setValue(component, field, value);
		}
	}
	
	protected void setValue(final Object obj, final Field f, final String xmlValue) throws NullPointerException {
		Object        value = null;
		final boolean flag  = f.canAccess(obj);
		
		this.addNecessarilyLoaders(f.getAnnotation(NecessarilyLoaders.class));
		
		final MainLoader ml = f.getAnnotation(MainLoader.class);
		if(ml != null) {
			final Loader l = ClassUtil.newInstance(ml.value());
			if(l.isLoadedClass(f.getType())) try {
				value = l.load(f.getType(), xmlValue);
			}catch(final Exception e) {
			}
			else Log.warn(MainLoader.class.getName() + " of " + f + " not load his class");
		}
		Object def = null;
		f.setAccessible(true);
		try {
			def = f.get(obj);
		}catch(final IllegalArgumentException e1) {
		}catch(final IllegalAccessException e1) {
		}finally {
			f.setAccessible(flag);
		}
		
		try {
			if(value == null) value = this.loaders.load(f.getType(), xmlValue);
		}catch(final Exception e) {
			if(def == null)
				throw e;
			else
				value = def;
		}
		if(value != null) {
			f.setAccessible(true);
			try {
				f.set(obj, value);
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}finally {
				f.setAccessible(flag);
			}
		}
	}
	
}
