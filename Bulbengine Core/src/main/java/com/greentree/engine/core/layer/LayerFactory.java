package com.greentree.engine.core.layer;

import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public final class LayerFactory {

	private final Map<String, Layer> map = new HashMap<>();

	public Layer get(final String name) {
		final var v = map.get(name);
		if(v != null) return v;
		final var k = new Layer(name);
		map.put(name, k);
		return k;
	}

}
