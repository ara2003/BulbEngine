package com.greentree.engine.editor.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.greentree.engine.Builder;
import com.greentree.engine.Game;
import com.greentree.engine.GameComponent;
import com.greentree.engine.GameNode;
import com.greentree.engine.Log;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.loading.ResourceLoader;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.util.Pair;
import com.greentree.xml.XMLElement;

public class BasicXMlBuilder extends Builder<XMLElement> {
	
	private final List<String> packages = new ArrayList<>(
			List.of("", Transform.class.getPackageName() + ".", Button.class.getPackageName() + ".",
					ColliderComponent.class.getPackageName() + ".", Button.class.getPackageName() + "."));
	private final Map<String, String> prefab = new HashMap<>();
	private final List<ClassLoader<?>> list = new ArrayList<>();
	
	public BasicXMlBuilder() {
		list.add(new MeshLoader());
		list.add(new IntegerLoader());
		list.add(new TextureLoader());
	}
	
	@SuppressWarnings("unchecked")
	private GameComponent createComponent(XMLElement xmlElement) {
		Class<? extends GameComponent> clazz = (Class<? extends GameComponent>) Game
				.loadClass(xmlElement.getAttribute("type"), packages);
		try {
			GameComponent component = clazz.getConstructor().newInstance();
			for(final Field field : ClassUtil.getAllFields(component.getClass()))
				if(field.getAnnotation(EditorData.class) != null) try {
					String xmlValue = null;
					{
						String xmlName = field.getAnnotation(EditorData.class).name();
						if(xmlName.equals("")) xmlName = field.getName();
						if(xmlName.equals("type"))
							Log.error("name of field " + component.getClass() + "." + field.getName() + " is \"type\"");
						xmlValue = xmlElement.getAttribute(xmlName, null);
					}
					if(xmlValue == null) xmlValue = field.getAnnotation(EditorData.class).def();
					if(!parse(component, field, xmlValue)) {
						Log.warn(field + " has annotation " + EditorData.class.getSimpleName() + " value not convert");
					}
				}catch(IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			return component;
		}catch(final Exception e) {
			Log.warn("not create component " + xmlElement.getAttribute("type"), e);
			return null;
		}
	}
	
	@Override
	protected void createNode(GameNode node, XMLElement in) {
		for(final XMLElement element : in.getChildrens("package")) packages.add(element.getContent() + ".");
		for(final XMLElement element : in.getChildrens("object_prefab")) prefab
				.put(element.getContent().substring(element.getContent().lastIndexOf('\\') + 1), element.getContent());
		for(final XMLElement element : in.getChildrens("tags"))
			for(final XMLElement element1 : element.getChildrens("tag")) node.addTag(element1.getAttribute("name"));
		for(final XMLElement e : in.getChildrens("system"))
			node.addSystem(GameSystem.createSystem(Game.loadClass(e.getContent(), packages)));
		List<Pair<GameNode, XMLElement>> bufer = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			GameNode сhildren = new GameNode(getNodeName(element));
			bufer.add(new Pair<>(сhildren, element));
			node.addChildren(сhildren);
		}
		for(Pair<GameNode, XMLElement> pair : bufer) {
			createNode(pair.first, pair.second);
		}
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = createComponent(element);
			if(component == null) continue;
			node.addComponent(component);
		}
	}
	
	private GameNode createNode(InputStream in) {
		XMLElement data = parse(in);
		GameNode node = new GameNode(getNodeName(data));
		createNode(node, data);
		return node;
	}
	
	@Override
	public GameNode createNode(String prefab) {
		String get = this.prefab.get(prefab);
		if(get != null) prefab = get;
		return createNode(ResourceLoader.getResourceAsStream(prefab + ".obj"));
	}
	
	@Override
	public String getNodeName(XMLElement xmlElement) {
		return xmlElement.getAttribute("name", "name");
	}
	
	@SuppressWarnings("unchecked")
	protected Object getValue(String xmlValue, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		if(clazz.equals(float.class)) return Float.parseFloat(xmlValue);
		Object result = null;
		for(ClassLoader<?> loader : list) {
			try {
				result = loader.load(xmlValue);
				if(result != null) break;
			}catch(Exception e) {
				Log.superWarn(e);
			}
		}
		if(result != null) return result;
		if(clazz.equals(String.class)) return xmlValue;
		if(clazz.equals(boolean.class)) return Boolean.parseBoolean(xmlValue);
		if(clazz.isEnum()) return Enum.valueOf(clazz.asSubclass(Enum.class), xmlValue);
		try {
			Field field = clazz.getField(xmlValue);
			if(Modifier.isStatic(field.getModifiers())) return field.get(null);
		}catch(NoSuchFieldException e) {
		}catch(SecurityException e) {
			e.printStackTrace();
		}
		if(GameComponent.class.isAssignableFrom(clazz)) {
			List<GameNode> list = Game.getMainNode().findNodesWithName(xmlValue);
			if(!list.isEmpty()) return list.get(0).getComponent(clazz.asSubclass(GameComponent.class));
		}
		return null;
	}
	
	/*
	 * closes the stream
	 */
	@Override
	public XMLElement parse(InputStream inputStream) {
		try {
			return new XMLElement(inputStream);
		}catch(ParserConfigurationException | SAXException | IOException e) {
			Log.warn(e);
			return null;
		}
	}
	
	protected boolean parse(Object obj, Field f, String xmlValue)
			throws IllegalArgumentException, IllegalAccessException {
		if(!xmlValue.isEmpty()) {
			f.setAccessible(true);
			f.set(obj, getValue(xmlValue, f.getType()));
			if(f.get(obj) == null) {
				Log.warn(obj + "." + f.getName() + " is not initialize");
				return false;
			}
		}
		return true;
	}
}

/** @author Arseny Latyshev */
abstract class ClassLoader<T> {
	
	public abstract T load(String value) throws Exception;
}
