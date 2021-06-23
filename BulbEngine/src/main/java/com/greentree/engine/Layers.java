package com.greentree.engine;

import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.layer.Layer;
import com.greentree.engine.layer.LayerComponent;
import com.greentree.engine.layer.LayerFactory;

public abstract class Layers {

	private static final LayerFactory layerFactory = new LayerFactory();
/**
 * @deprecated use get
 * @param value
 * @return
 */
	@Deprecated
	public static Layer getLayer(String value) {
		return layerFactory.get(value);
	}
	
	public static Layer get(String value) {
		return layerFactory.get(getLayerName(value));
	}

	protected static String getLayerName(final String v) {
		if(v == null) return "default";
		if(v.isBlank()) return "default";
		return v;
	}

	public static Layer get(GameObject object) {
		LayerComponent layer = object.getComponent(LayerComponent.class);
		if(layer == null)return layerFactory.get("default");
		return layer.getLayer();
	}
}
