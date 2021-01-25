package com.greentree.engine;

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

import com.greentree.engine.component.Transform;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.gui.ui.Button;
import com.greentree.engine.input.Input;
import com.greentree.engine.loading.ResourceLoader;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.xml.XMLElement;


/**
 * @author Arseny Latyshev
 *
 */
public class BasicXMlBuilder implements Builder {
	
	private final List<String> packages = new ArrayList<>(List.of("", Transform.class.getPackageName()+".", Button.class.getPackageName()+".", ColliderComponent.class.getPackageName()+".", Button.class.getPackageName()+"."));
	private final Map<String, String> prefab = new HashMap<>();
	
	public BasicXMlBuilder(){
	}
	
	@SuppressWarnings("unchecked")
	public static Object getValue(String xmlValue, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		if(clazz.equals(String.class)) return xmlValue;
		if(clazz.equals(int.class)) return parseInt(xmlValue);
		if(clazz.equals(float.class)) return Float.parseFloat(xmlValue);
		if(clazz.equals(boolean.class)) return Boolean.parseBoolean(xmlValue);
		if(clazz.isEnum()) return Enum.valueOf(clazz.asSubclass(Enum.class), xmlValue);
		
		try{
			Field field = clazz.getField(xmlValue);
			if(Modifier.isStatic(field.getModifiers()))
				return field.get(null);
		}catch(NoSuchFieldException e) {
		}catch(SecurityException e) {
			e.printStackTrace();
		}
		
		if(GameComponent.class.isAssignableFrom(clazz)) {
			//			List<GameNode> list = parent.findNodes(node -> {
			//				return true;//node.hasComponent(clazz)&&(node.getName().equals(xmlValue));
			//			});
			//			System.out.println(list);
			//			if(!list.isEmpty())return list.get(0);
		}
		return null;
	}
	
	public static boolean parse(Object obj, Field f, String xmlValue) throws IllegalArgumentException, IllegalAccessException {
		if(!xmlValue.isEmpty()) {
			f.setAccessible(true);
			
			f.set(obj, getValue(xmlValue, f.getType()));
			
			if(f.get(obj) == null) {
				Log.warn(obj + "." + f.getName() + " is not initialize");
				return false;
			}
			return true;
		}
		return true;
	}
	public static int parseInt(String value) {
		if(value.startsWith("key::"))return Input.getKeyIndex(value.substring(5));
		return Integer.parseInt(value);
	}
	
	@SuppressWarnings("unchecked")
	private GameComponent createComponent(GameNodeBuilder parent, XMLElement xmlElement) {
		Class<? extends GameComponent>	clazz = (Class<? extends GameComponent>) Game.loadClass(xmlElement.getAttribute("type"), packages);
		try {
			GameComponent component = clazz.getConstructor().newInstance();
			for(final Field f : ClassUtil.getAllFields(component.getClass()))
				if(f.getAnnotation(EditorData.class) != null) try {
					String xmlValue = null;
					{
						String xmlName = f.getAnnotation(EditorData.class).name();
						if(xmlName.equals(""))xmlName = f.getName();
						if(xmlName.equals("type"))
							Log.error("name of field " + component.getClass() + "." + f.getName() + " is \"type\"");
						xmlValue = xmlElement.getAttribute(xmlName, null);
					}
					if(xmlValue == null) xmlValue = f.getAnnotation(EditorData.class).def();
					
					if(!parse(component, f, xmlValue)) {
						Log.warn(component.getClass() + " " + f + " has annotation " + EditorData.class.getSimpleName() + " value not convert");
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
	
	private GameNode createNode(GameNodeBuilder parent, XMLElement in) {
		GameNodeBuilder builder = new  GameNodeBuilder(in.getAttribute("name", "object"));
		for(final XMLElement element : in.getChildrens("package"))
			packages.add(element.getContent()+".");
		
		for(final XMLElement element : in.getChildrens("object_prefab"))
			prefab.put(element.getContent().substring(element.getContent().lastIndexOf('\\')+1), element.getContent());
		
		for(final XMLElement element : in.getChildrens("tags"))
			for(final XMLElement element1 : element.getChildrens("tag"))
				builder.addTag(element1.getAttribute("name"));
		
		for(final XMLElement e : in.getChildrens("system"))
			builder.addSystem((GameSystem.createSystem(Game.loadClass(e.getContent(), packages))));
		
		for(final XMLElement e : in.getChildrens("object"))builder.addNode(createNode(builder, e));
		
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = createComponent(builder, element);
			if(component == null)continue;
			builder.addComponent(component);
		}
		return builder.get();
	}
	
	@Override
	public GameNode createNode(InputStream data) {
		return createNode(null, parse(data));
	}
	
	@Override
	public GameNode createNode(String prefab) {
		String get = this.prefab.get(prefab);
		if(get != null)prefab = get;
		return createNode(ResourceLoader.getResourceAsStream(prefab+".obj"));
	}
	
	private XMLElement parse(InputStream input) {
		try {
			return new XMLElement(input);
		}catch(ParserConfigurationException | SAXException | IOException e) {
			Log.warn(e);
			return null;
		}
	}
}
