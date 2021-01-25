package com.greentree.engine.component.collider;

import com.greentree.engine.component.util.EditorData;
import com.greentree.geom.Rectangle;

/** @author ara */
public final class RectangleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	
	@EditorData
	private float width, height;
	
	@Override
	public void start() {
		shape = new Rectangle(0, 0, width, height);
		super.start();
	}
	
}
