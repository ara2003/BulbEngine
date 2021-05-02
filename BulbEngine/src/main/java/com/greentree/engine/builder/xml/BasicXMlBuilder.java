package com.greentree.engine.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.greentree.engine.core.builder.AbstractBuilder;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {
	
	private final LoaderList loaders = new LoaderList();
	
	private final Collection<String> packages;
	
	public BasicXMlBuilder(final String... packages) {
		this.packages = new ArrayList<>(packages.length);
		Collections.addAll(this.packages, packages);
		
		loaders.addLoader(new FloatLoader());
		loaders.addLoader(new IntegerLoader());
		loaders.addLoader(new TextureLoader());
		loaders.addLoader(new StaticFieldLoader());
		loaders.addLoader(new StringLoader());
		loaders.addLoader(new ObjMeshLoader());
		loaders.addLoader(new BooleanLoader());
		loaders.addLoader(new EnumLoader());
		loaders.addLoader(new GameComponentLoader());
		loaders.addLoader(new DoubleLoader());
		loaders.addLoader(new ShortLoader());
		loaders.addLoader(new ByteLoader());
		loaders.addLoader(new CharLoader());
		loaders.addLoader(new ShaderProgramLoader());
	}
	
	private void addNecessarilyLoaders(final NecessarilyLoaders annotation) {
		if(annotation == null) return;
		for(final Class<? extends Loader> cl : annotation.value()) if(!loaders.hasLoader(cl)) {
			final Loader l = ClassUtil.newInstance(cl);
			loaders.addLoader(l);
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
		final Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) loadClass(type);
		return this.createComponent(clazz);
	}
	
	
	@Override
	public GameObject createPrefab(final String prefabPath, final GameObjectParent parent) {
		final InputStream in     = ResourceLoader.getResourceAsStream(prefabPath + ".xml");
		final GameObject  object = this.createObject(in, parent);
		this.fillObject(object, in);
		popComponents();
		object.initSratr();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	private GameSystem createSystem(final XMLElement xmlElement) {
		final Class<? extends GameSystem> clazz = (Class<? extends GameSystem>) loadClass(xmlElement.getContent());
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
			setFields(component, map);
		}catch(final Exception e) {
			Log.warn("not create component " + in.getAttribute("type"), e);
		}
	}
	
	@Override
	protected void fillObject(final GameObject object, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) packages.add(element.getContent() + ".");
		
		final List<Pair<GameObject, XMLElement>> contextObject = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, object);
			contextObject.add(new Pair<>(сhildren, element));
			object.addChildren(сhildren);
		}
		
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = this.createComponent(element);
			if(component == null) continue;
			pushComponent(component, element);
			object.addComponent(component);
		}
		
		for(final Pair<GameObject, XMLElement> element : contextObject) this.fillObject(element.first, element.second);
		
		for(final XMLElement element : in.getChildrens("package")) packages.remove(element.getContent() + ".");
	}
	
	@Override
	protected void fillScene(final GameScene scene, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) packages.add(element.getContent());
		
		for(final XMLElement el : in.getChildrens("system")) {
			final GameSystem component = createSystem(el);
			if(component == null) continue;
			scene.addSystem(component);
		}
		
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, scene);
			if(сhildren == null) continue;
			this.fillObject(сhildren, element);
			scene.addChildren(сhildren);
		}
		
		popComponents();
		
		for(final XMLElement element : in.getChildrens("package")) packages.remove(element.getContent());
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
		if(checkXmlNameFormat(xmlName)) return xmlName;
		xmlName = field.getName();
		if(checkXmlNameFormat(xmlName)) return xmlName;
		throw new IllegalArgumentException("could not find a suitable name of " + field);
	}
	
	private String getXmlValue(final Field field, final Map<String, String> attributes) {
		final String xmlValue = attributes.get(getXmlName(field));
		return xmlValue;
	}
	
	protected final Class<?> loadClass(final String name) {
		return ClassUtil.loadClass(name, packages);
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
			for(final Field field : allEditorDataFields) xmlNamesToFind.add(getXmlName(field));
			for(final String attribute : attributes.keySet())
				if(!xmlNamesToFind.contains(attribute))
					Log.warn("xml element " + component + " has value " + attribute + " and it is not used. [xmlNamesToFind=" + xmlNamesToFind + " attributes=" + attributes + "]");//не часть алгоритма
		}
		for(final Field field : allEditorDataFields) {
			String value = getXmlValue(field, attributes);
			if(value == null) value = getDefoultValue(field);
			setValue(component, field, value);
		}
	}
	
	protected void setValue(final Object obj, final Field f, final String xmlValue) throws NullPointerException {
		Object        value = null;
		final boolean flag  = f.canAccess(obj);
		
		addNecessarilyLoaders(f.getAnnotation(NecessarilyLoaders.class));
		
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
			if(value == null) value = loaders.load(f.getType(), xmlValue);
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
