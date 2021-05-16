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
import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.Loader;
import com.greentree.data.loaders.LoaderList;
import com.greentree.data.loaders.NecessarilyLoaders;
import com.greentree.data.loaders.collection.ListLoader;
import com.greentree.data.loaders.collection.MapLoader;
import com.greentree.data.loaders.collection.TableLoader;
import com.greentree.data.loaders.value.BooleanLoader;
import com.greentree.data.loaders.value.ByteLoader;
import com.greentree.data.loaders.value.CharLoader;
import com.greentree.data.loaders.value.DoubleLoader;
import com.greentree.data.loaders.value.EnumLoader;
import com.greentree.data.loaders.value.FloatLoader;
import com.greentree.data.loaders.value.IntegerLoader;
import com.greentree.data.loaders.value.ShortLoader;
import com.greentree.data.loaders.value.StaticFieldLoader;
import com.greentree.data.loaders.value.StringLoader;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.engine.builder.loaders.GameComponentLoader;
import com.greentree.engine.builder.loaders.IntegerConstLoader;
import com.greentree.engine.builder.loaders.LayerLoader;
import com.greentree.engine.builder.loaders.ObjMeshLoader;
import com.greentree.engine.builder.loaders.ShaderProgramLoader;
import com.greentree.engine.builder.loaders.TextureLoader;
import com.greentree.engine.core.builder.AbstractBuilder;
import com.greentree.engine.core.builder.EditorData;
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
		loaders.addLoader(new IntegerConstLoader());
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
		loaders.addLoader(new LayerLoader());

		loaders.addLoader(new ListLoader());
		loaders.addLoader(new MapLoader());
		loaders.addLoader(new TableLoader());
		
	}
	
	private void addNecessarilyLoaders(final NecessarilyLoaders annotation) {
		if(annotation == null) return;
		for(final Class<? extends Loader> cl : annotation.value()) if(!loaders.hasLoader(cl)) {
			final Loader l = ClassUtil.newInstance(cl);
			loaders.addLoader(l);
		}
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
		try {
			final Class<? extends GameSystem> clazz = (Class<? extends GameSystem>) loadClass(xmlElement.getAttribute("type"));
			return createSystem(clazz);
		}catch(final Exception e) {
			Log.warn("system not create " + xmlElement, e);
			return null;
		}
	}
	
	@Override
	public void fillComponent(final GameComponent component, final XMLElement in) {
		try {
			setFields(component, in);
		}catch(final Exception e) {
			Log.warn("not create component " + in, e);
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
			final GameSystem system = createSystem(el);
			if(system == null) continue;
			scene.addSystem(system);
			pushSystem(system, el);
		}
		
		for(final XMLElement element : in.getChildrens("layer")) scene.addLayer(getLayer(element));
		
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, scene);
			if(сhildren == null) continue;
			scene.addChildren(сhildren);
			this.fillObject(сhildren, element);
		}
		
		popComponents();
		popSystems();
		
		for(final XMLElement element : in.getChildrens("package")) packages.remove(element.getContent());
	}
	
	
	@Override
	protected void fillSystem(final GameSystem system, final XMLElement in) {
		try {
			setFields(system, in);
		}catch(final Exception e) {
			Log.warn("not create component " + in, e);
		}
	}
	
	@Override
	protected String getLayerName(final XMLElement in) {
		final var v = in.getAttribute("layer");
		if(v == null) return "default";
		if(v.isBlank()) return "default";
		return v;
	}
	
	@Override
	protected String getName(final XMLElement in) {
		return in.getAttribute("name", "name");
	}
	
	private static String getName(final Field field) {
		String xmlName = field.getAnnotation(EditorData.class).name();
		if(!xmlName.isBlank()) return xmlName;
		xmlName = field.getName();
		if(!xmlName.isBlank()) return xmlName;
		throw new IllegalArgumentException("could not find a suitable name of " + field);
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
	
	private void setFields(final Object object, final XMLElement attributes) {
		final List<Field> allEditorDataFields = ClassUtil.getAllFieldsWithAnnotation(object.getClass(), EditorData.class);
		{//Validator
			final Set<String> xmlNamesToFind = new HashSet<>(allEditorDataFields.size());
			for(final Field field : allEditorDataFields) xmlNamesToFind.add(BasicXMlBuilder.getName(field));
			for(final XMLElement attribute : attributes.getChildrens())
				if(!xmlNamesToFind.contains(getName(attribute)))
					Log.warn("xml element " + object + " has value " + attribute + " and it is not used. [xmlNamesToFind=" + xmlNamesToFind + " attributes=" + attributes + "]");//не часть алгоритма
		}
		addNecessarilyLoaders(object.getClass().getAnnotation(NecessarilyLoaders.class));
		final Map<String, XMLElement> attributes0 = new HashMap<>(attributes.getChildrens().size());
		for(final var a : attributes.getChildrens()) attributes0.put(a.getAttribute("name"), a);
		for(final Field field : allEditorDataFields) {
			try {
				setValue(object, field, attributes0.get(BasicXMlBuilder.getName(field)));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected Object setValue(final Object obj, final Field f, final XMLElement xmlValue) throws Exception {
		final boolean flag = f.canAccess(obj);
		Object        def  = null;
		f.setAccessible(true);
		try {
			def = f.get(obj);
		}catch(final IllegalArgumentException e1) {
		}catch(final IllegalAccessException e1) {
		}finally {
			f.setAccessible(flag);
		}
		Object value;
		try {
			value = loaders.parse(f, xmlValue);
		}catch(final Exception e) {
			if(def != null)
				value = def;
			else
				throw e;
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
		return value;
	}
	
}
