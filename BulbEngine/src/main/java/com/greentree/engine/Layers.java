package com.greentree.engine;

import com.greentree.common.ClassUtil;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.layer.Layer;
import com.greentree.engine.layer.LayerComponent;
import com.greentree.engine.layer.LayerFactory;

public abstract class Layers {

	private static final LayerFactory layerFactory = new LayerFactory();

	public static Layer get(GameObject object) {
		LayerComponent layer = object.getComponent(LayerComponent.class, false);
		if(layer == null) {
			Layers.setLayer(object, "default");
			return get(object);
		}
		return layer.getLayer();
	}

	public static Layer get(String value) {
		return layerFactory.get(getLayerName(value));
	}

	protected static String getLayerName(final String v) {
		if(v == null || v.isBlank()) return "default";
		return v;
	}

	public static void setLayer(GameObject object, String layer) {
		layer = getLayerName(layer);
		if(object.getComponent(LayerComponent.class, false) == null) {
			final LayerComponent component = new LayerComponent();
			ClassUtil.setField(component, "layer", Layers.get(layer));
			object.addComponent(component);
		}else throw new RuntimeException(layer);
	}
}
