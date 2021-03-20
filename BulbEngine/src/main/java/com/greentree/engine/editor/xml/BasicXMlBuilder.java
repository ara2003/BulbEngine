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

import com.greentree.engine.Game;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.GameComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.editor.AbstractBuilder;
import com.greentree.engine.object.GameNode;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameScene;
import com.greentree.engine.system.GameSystem;
import com.greentree.loading.ResourceLoader;
import com.greentree.util.ClassUtil;
import com.greentree.util.Log;
import com.greentree.util.OneClassSet;
import com.greentree.util.Pair;
import com.greentree.xml.XMLElement;

public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {
	
	private static final Map<Class<? extends Loader>, Integer> map = new HashMap<>();
	private final List<String> packages = new ArrayList<>(List.of("", Transform.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", ColliderComponent.class.getPackageName() + ".",
			Button.class.getPackageName() + ".", GameSystem.class.getPackageName() + "."));
	private final Map<String, String> prefab = new HashMap<>();
	private final OneClassSet<Loader> loaders = new OneClassSet<>(List.of(new FloatLoader(), new IntegerLoader(),
			new TextureLoader(), new StaticFieldLoader(), new StringLoader(), new MeshLoader(), new BooleanLoader(),
			new EnumLoader(), new GameComponentLoader(), new DoubleLoader(), new ShortLoader(), new ByteLoader(),
			new CharLoader()));
	
	public BasicXMlBuilder() {
	}
	
	/** @return can xmlName be the name of the parameter */
	private boolean checkXmlNameFormat(final String xmlName) {
		if(xmlName.equals("type")) return false;
		return xmlName.isEmpty() == false;
	}
	
	private final void count(final Class<? extends Loader> clazz) {
		Integer i = BasicXMlBuilder.map.remove(clazz);
		if(i == null) i = 0;
		i++;
		BasicXMlBuilder.map.put(clazz, i);
		final ArrayList<Entry<Class<? extends Loader>, Integer>> list = new ArrayList<>(
				BasicXMlBuilder.map.entrySet());
		list.sort((a, b)->b.getValue().compareTo(a.getValue()));
		list.forEach(a->Log.debug(String.format("%s(%d) ", a.getKey().getSimpleName(), a.getValue())));
		Log.debug("\n");
	}
	
	@SuppressWarnings("unchecked")
	private GameComponent createComponent(final XMLElement xmlElement) {
		final Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) Game.loadClass(xmlElement.getAttribute("type"), this.packages);
		try {
			final GameComponent component = clazz.getConstructor().newInstance();
			this.setFields(component, ClassUtil.getAllFieldsWithAnnotation(component.getClass(), EditorData.class),
					xmlElement.getAttributes());
			return component;
		}catch(final Exception e) {
			Log.warn("not create component " + xmlElement.getAttribute("type"), e);
			return null;
		}
	}
	
	@Override
	public GameObject createObject(String prefab, final GameNode parent) {
		final String get = this.prefab.get(prefab);
		if(get != null) prefab = get;
		final XMLElement data   = this.parse(ResourceLoader.getResourceAsStream(prefab + ".node"));
		GameObject.Builder builder = GameObject.builder(this.getName(data), parent);
		this.fillObject(builder, data);
		GameObject res = builder.get();
		res.init();
		return res;
	}
	
	@Override
	protected void fillObject(final GameObject.Builder object, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		final List<Pair<GameObject.Builder, XMLElement>> bufer = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject.Builder сhildren = GameObject.builder(this.getName(element), object.get());
			bufer.add(new Pair<>(сhildren, element));
		}
		for(final Pair<GameObject.Builder, XMLElement> pair : bufer) this.fillObject(pair.first, pair.second);
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = this.createComponent(element);
			if(component == null) continue;
			object.addComponent(component);
		}
		for(final XMLElement element : in.getChildrens("package")) this.packages.remove(element.getContent() + ".");
	}
	
	@Override
	protected void fillScene(final GameScene.Builder scene, final XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) this.packages.add(element.getContent() + ".");
		for(final XMLElement element : in.getChildrens("object_prefab")) this.prefab
				.put(element.getContent().substring(element.getContent().lastIndexOf('\\') + 1), element.getContent());
		for(final XMLElement el : in.getChildrens("system"))
			scene.addSystem(GameSystem.createSystem(Game.loadClass(el.getContent(), this.packages)));
		final List<Pair<GameObject.Builder, XMLElement>> bufer = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject.Builder сhildren = GameObject.builder(this.getName(element), scene.get());
			bufer.add(new Pair<>(сhildren, element));
		}
		for(final Pair<GameObject.Builder, XMLElement> pair : bufer) this.fillObject(pair.first, pair.second);
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
	
	private void setFields(final Object component, final List<Field> allEditorDataFields,
			final Map<String, String> attributes) {
		final Set<String> fieldNames = new HashSet<>(allEditorDataFields.size());
		for(final Field field : allEditorDataFields) fieldNames.add(this.getXmlName(field));
		for(final String attribute : attributes.keySet())
			if(attribute.equals("type") == false && fieldNames.contains(attribute) == false)
				Log.warn("xml element " + component + " has value " + attribute + " and it is not used");
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

/** @author Arseny Latyshev */
abstract class AbstractLoader<C> implements Loader {
	
	private final Class<C> clazz;
	
	public AbstractLoader(final Class<C> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public final Object load(final Class<?> fieldClass, final String value) throws Exception {
		if(fieldClass.isAssignableFrom(this.clazz)) return this.load(value);
		return null;
	}
	
	public abstract Object load(String value) throws Exception;
}

interface Loader {
	
	Object load(Class<?> fieldClass, String value) throws Exception;
}

abstract class PairLoader<C> implements Loader {
	
	private final Class<C> classa, classb;
	
	public PairLoader(final Class<C> classa, final Class<C> classb) {
		if(classa.equals(classb)) throw new IllegalArgumentException("classa can not by equals classb");
		this.classa = classa;
		this.classb = classb;
	}
	
	@Override
	public final Object load(final Class<?> fieldClass, final String value) throws Exception {
		if(this.classb.isAssignableFrom(fieldClass)) return this.load(value);
		if(this.classa.isAssignableFrom(fieldClass)) return this.load(value);
		return null;
	}
	
	public abstract Object load(String value) throws Exception;
}
