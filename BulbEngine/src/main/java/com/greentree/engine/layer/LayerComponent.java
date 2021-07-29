package com.greentree.engine.layer;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.node.GameComponent;


public class LayerComponent extends GameComponent {

	@EditorData
	private Layer layer;

	public Layer getLayer() {
		return layer;
	}

}
