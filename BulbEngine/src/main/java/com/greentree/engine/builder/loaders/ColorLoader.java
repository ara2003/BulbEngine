package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.graphics.Color;

public class ColorLoader extends AbstractLoader<Color> implements ValueLoader{

	public ColorLoader() {
		super(Color.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(Class<T> clazz, String value) throws Exception {
		if(!value.startsWith("#"))throw new Exception("not start with #");
		value = value.substring(1);
		int n = Integer.parseInt(value, 16);
		if(value.length() == 3)return (T) new Color((n/(16*16)%16)*16, (n/(16)%16)*16, (n%16)*16);
		if(value.length() == 6)return (T) new Color(n/256/256%256, n/256%256, n%256);
		throw new Exception("unlegal size");
		
	}

}
