package com.greentree.engine.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.ConstructorLoader;
import com.greentree.data.loaders.LoaderList;
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
import com.greentree.engine.Layers;
import com.greentree.engine.builder.loaders.ColorLoader;
import com.greentree.engine.builder.loaders.GameComponentLoader;
import com.greentree.engine.builder.loaders.GameObjectLoader;
import com.greentree.engine.builder.loaders.IntegerConstLoader;
import com.greentree.engine.builder.loaders.LayerLoader;
import com.greentree.engine.builder.loaders.ObjMeshLoader;
import com.greentree.engine.builder.loaders.ShaderProgramLoader;
import com.greentree.engine.builder.loaders.TextureLoader;
import com.greentree.engine.core.builder.AbstractBuilder;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.layer.LayerComponent;

/** @author Arseny Latyshev */
public class BasicXMlBuilder extends AbstractBuilder<XMLElement> {

	private final LoaderList loaders = new LoaderList();
	{
		loaders.addParser(new FloatLoader());
		loaders.addParser(new IntegerConstLoader());
		loaders.addParser(new IntegerLoader());
		loaders.addParser(new TextureLoader());
		loaders.addParser(new StringLoader());
		loaders.addParser(new ObjMeshLoader());
		loaders.addParser(new BooleanLoader());
		loaders.addParser(new EnumLoader());
		loaders.addParser(new GameComponentLoader());
		loaders.addParser(new DoubleLoader());
		loaders.addParser(new ShortLoader());
		loaders.addParser(new ByteLoader());
		loaders.addParser(new CharLoader());
		loaders.addParser(new ShaderProgramLoader());
		loaders.addParser(new LayerLoader());
		loaders.addParser(new StaticFieldLoader());

		loaders.addParser(new ListLoader());
		loaders.addParser(new MapLoader());
		loaders.addParser(new TableLoader());

		loaders.addParser(new GameObjectLoader());
		loaders.addParser(new ConstructorLoader());
		
		loaders.addParser(new ColorLoader());
	}

	@Override
	public GameObject createPrefab(final String name, final String prefabPath, final GameObjectParent parent) {
		final XMLElement in     = parse(ResourceLoader.getResourceAsStream(prefabPath + ".xml"));
		final GameObject  object = new GameObject(name+"#"+getObjectName(in), parent);
		this.fillObject(object, in);
		popComponents();
		object.initSratr();
		return object;
	}

	@Override
	protected void fillObject(final GameObject object, final XMLElement in) {
		var layer = in.getAttribute("layer");
		if(layer != null && !layer.isBlank()) {
			final LayerComponent component = new LayerComponent();
			ClassUtil.setField(component, "layer", Layers.get(layer));
			object.addComponent(component);
		}

		final List<Pair<GameObject, XMLElement>> contextObject = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, object);
			if(сhildren == null)continue;
			contextObject.add(new Pair<>(сhildren, element));
		}

		for(final XMLElement element : in.getChildrens("component")) {
			final GameComponent component = this.createComponent(element);
			if(component == null) continue;
			pushComponent(component, element);
			object.addComponent(component);
		}
	
		for(var var : contextObject)fillObject(var.first, var.second);
	}

	@Override
	protected void fillScene(final GameScene scene, final XMLElement in) {
		for(final XMLElement el : in.getChildrens("system")) {
			final GameSystem system = createSystem(el);
			if(system == null) continue;
			scene.addSystem(system);
			pushSystem(system, el);
		}

		final List<Pair<GameObject, XMLElement>> contextObject = new ArrayList<>();
		for(final XMLElement element : in.getChildrens("object")) {
			final GameObject сhildren = this.createObject(element, scene);
			if(сhildren == null) continue;
			contextObject.add(new Pair<>(сhildren, element));
		}

		for(var var : contextObject)fillObject(var.first, var.second);
		popComponents();
		popSystems();
	}


	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends GameComponent> getComponentClass(final XMLElement xmlElement) {
		final String type = xmlElement.getAttribute("type");
		if(type.isBlank()) throw new IllegalArgumentException(xmlElement + " does not contain atribute \"type\"");
		return (Class<? extends GameComponent>) ClassUtil.loadClass(type);
	}

	@Override
	protected String getObjectName(final XMLElement in) {
		return in.getAttribute("name", "name");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends MultiBehaviour> getMultiBehaviourClass(XMLElement in) {
		final String type = in.getAttribute("type");
		if(type.isBlank()) throw new IllegalArgumentException(in + " does not contains atribute \"type\"");
		return (Class<? extends MultiBehaviour>) ClassUtil.loadClass(type);
	}


	@Override
	public Object load(Field field, XMLElement xmlValue) throws Exception {
		return loaders.parse(field, xmlValue);
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

	@Override
	protected void setFields(final Object object, final XMLElement attributes) {
		if(object.getClass().getAnnotation(Deprecated.class) != null)Log.warn("load Deprecated class " + object.getClass().getName());//Validator
		final List<Field> allEditorDataFields = ClassUtil.getAllFieldsHasAnnotation(object.getClass(), EditorData.class);
		{//Validator
			final Set<String> xmlNamesToFind = new HashSet<>(allEditorDataFields.size());
			for(final Field field : allEditorDataFields) xmlNamesToFind.add(BasicXMlBuilder.getNameOfField(field));
			for(final XMLElement attribute : attributes.getChildrens())
				if(!xmlNamesToFind.contains(getObjectName(attribute)))
					Log.warn("xml element " + object + " has value " + attribute + " and it is not used. [xmlNamesToFind=" + xmlNamesToFind + " attributes=" + attributes + "]");//не часть алгоритма
		}
		final Map<String, XMLElement> attributes0 = new HashMap<>(attributes.getChildrens().size());
		for(final var a : attributes.getChildrens()) attributes0.put(a.getAttribute("name"), a);
		for(final Field field : allEditorDataFields) try {
			XMLElement xml = attributes0.get(BasicXMlBuilder.getNameOfField(field));
			if(xml == null && required(field))
				throw new IllegalArgumentException(String.format("required field(%s) not listed in %s", field, attributes));
			setValue(object, field, xml);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
