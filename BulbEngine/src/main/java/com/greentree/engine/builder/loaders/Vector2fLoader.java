package com.greentree.engine.builder.loaders;

import java.lang.reflect.Field;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.Vector2f;
import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.AbstractLoader;

public class Vector2fLoader extends AbstractLoader {

	public Vector2fLoader() {
		super(AbstractVector2f.class);
	}

	@Override
	public Object parse(Field field, XMLElement element, Object def) throws Exception {
		AbstractVector2f def0 = (AbstractVector2f)(def == null?new Vector2f():def);
		setVector(field, element, def0);
		return def0;
	}

	public void setVector(Field field, XMLElement element, AbstractVector2f def) throws Exception {
		for(final var a : element.getChildrens()) {
			float v = Float.parseFloat(a.getContent());
			switch(a.getAttribute("name")) {
				case "x" -> def.setX(v);
				case "y" -> def.setY(v);
				default ->
				throw new IllegalArgumentException("Unexpected value: " + a.getAttribute("name"));
			}
		}
	}

}
