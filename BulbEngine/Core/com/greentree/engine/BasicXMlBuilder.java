package com.greentree.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.serialize.ClassUtil;
import com.greentree.serialize.GsonFileParser;
import com.greentree.util.ClassList;
import com.greentree.xml.XMLElement;
import com.greentree.xml.XMLParser;


/**
 * @author Arseny Latyshev
 *
 */
public class BasicXMlBuilder implements Builder {

	private XMLElement parse(InputStream input) {
		try {
			return new XMLElement(input);
		}catch(ParserConfigurationException | SAXException | IOException e) {
			Log.warn(e);
			return null;
		}
	}

	@Override
	public GameComponent createComponent(InputStream data) {
		return createComponent(parse(data));
	}
	@Override
	public GameObject createObject(InputStream data) {
		return createObject(parse(data));
	}
	@Override
	public Scene createScene(InputStream data) {
		return createScene(parse(data));
	}

	private Scene createScene(XMLElement in) {
		Scene scene = new Scene();
		for(final XMLElement system : in.getChildrens("systems"))
			for(final XMLElement e : system.getChildrens("system"))
				scene.addSystem((GameSystem.createSystem(Game.loadClass(e.getAttribute("name")))));
		for(final XMLElement e : in.getChildrens("object")) scene.addObject(createObject(e));
		return scene;
	}

	@SuppressWarnings("unchecked")
	private static GameComponent createComponent(XMLElement data) {
		Class<? extends GameComponent>	clazz = (Class<? extends GameComponent>) Game.loadClass(data.getAttribute("type"));
		GameComponent component = null;
		try {
			component = clazz.getConstructor().newInstance();
		}catch(final Exception e) {
			Log.warn(data, e);
			return null;
		}
		for(final Field f : ClassUtil.getAllFields(component.getClass()))
			if(f.getAnnotation(EditorData.class) != null) try {
				String xmlValue = null;
				{
					String xmlName = f.getAnnotation(EditorData.class).name();
					if(xmlName.equals("")) xmlName = f.getName();
					if(xmlName.equals("type"))
						Log.error("name of field " + component.getClass() + "." + f.getName() + " is \"type\"");
					xmlValue = data.getAttribute(xmlName, null);
				}
				if(xmlValue == null) xmlValue = f.getAnnotation(EditorData.class).def();
				
				System.out.println(component + "." + f.getName() + " = " + xmlValue);
				
				if(xmlValue.isEmpty()||xmlValue == null) {
					Log.warn(f + " has annotation " + EditorData.class.getSimpleName() + " and value is not set");
					continue;
				}
				f.setAccessible(true);
				
				if(f.getType().equals(String.class)) f.set(component, xmlValue);
				if(f.getType().equals(int.class)) f.setInt(component, EditorParse.parseInt(xmlValue));
				if(f.getType().equals(float.class)) f.setFloat(component, Float.parseFloat(xmlValue));
				if(f.getType().equals(boolean.class)) f.setBoolean(component, Boolean.parseBoolean(xmlValue));
				if(f.getType().isEnum()) f.set(component, Enum.valueOf(f.getType().asSubclass(Enum.class), xmlValue));
				
				if(f.get(component) == null) f.set(component, new GsonFileParser().load(xmlValue, f.getType()));
				
				if(f.get(component) == null)Log.warn(component + "." + f.getName() + " is not initialize");
				
			}catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
				
		return component;
	}

	private GameObject createObject(XMLElement in) {
		GameObject object = new GameObject();
		
		if(!in.getAttribute("file").equals("")) {
			final String file = in.getAttribute("file");
			name = new File(file).getName().replaceFirst(".obj", "");
			in = XMLParser.parse(Game.getAssets(), file, "obj");
		}else {
			name = in.getAttribute("name");
		}
		corutines = new LinkedList<>();
		
		components = new ClassList<>();
		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = GameComponent.createComponent(element.getIputStream());
			if(component == null)continue;
			components.add(component);
		}

		objects = new ClassList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			objects.add(new GameObject(element));
		}
		
		tags = new HashSet<>();
		for(final XMLElement element : in.getChildrens("tags")) {
			for(final XMLElement element1 : element.getChildrens("tag")) {
				tags.add(element1.getAttribute("name"));
			}
		}
	}

}
