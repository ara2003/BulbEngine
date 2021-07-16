package com.greentree.engine.builder.loaders;

import java.lang.reflect.Field;

import com.greentree.common.math.vector.AbstractVector3f;
import com.greentree.common.math.vector.Vector3f;
import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.AbstractLoader;

public class Vector3fLoader extends AbstractLoader {

	public Vector3fLoader() {
		super(AbstractVector3f.class);
	}

	@Override
	public AbstractVector3f parse(Field field, XMLElement element, Object def) throws Exception {
		AbstractVector3f def0 = (AbstractVector3f)(def == null?new Vector3f():def);
		setVector(field, element, def0);
		return def0;
	}

	public void setVector(Field field, XMLElement element, AbstractVector3f def) throws Exception {
		for(final var a : element.getChildrens()) {
			float v = Float.parseFloat(a.getContent());
			switch(a.getAttribute("name")) {
				case "x" -> def.setX(v);
				case "y" -> def.setY(v);
				case "z" -> def.setZ(v);
				default ->
				throw new IllegalArgumentException("Unexpected value: " + a.getAttribute("name"));
			}
		}
	}

}
