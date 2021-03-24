package com.greentree.engine.component.collider;

import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Rectangle;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class RectangleColliderComponent extends ColliderComponent {
	
	@EditorData
	private float width, height;

	@Override
	protected Shape2D generateShape() {
		return new Rectangle(0, 0, width, height);
	}
}
