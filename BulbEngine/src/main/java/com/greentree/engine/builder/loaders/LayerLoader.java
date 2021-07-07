package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.Layers;
import com.greentree.engine.layer.Layer;

/** @author Arseny Latyshev */
public class LayerLoader extends AbstractLoader implements ValueLoader {

	public LayerLoader() {
		super(Layer.class);
	}

	@Override
	public Object parse(Class<?> clazz, String value) throws Exception {
		return Layers.get(value);
	}
}
