package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.AbstractLoader;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.engine.Layers;
import com.greentree.engine.layer.Layer;

/** @author Arseny Latyshev */
public class LayerLoader extends AbstractLoader<Layer> implements ValueLoader {

	public LayerLoader() {
		super(Layer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(Class<T> clazz, String value) throws Exception {
		return (T) Layers.getLayer(value);
	}
}
