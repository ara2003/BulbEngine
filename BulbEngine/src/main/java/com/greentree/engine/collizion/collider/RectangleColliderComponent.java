package com.greentree.engine.collizion.collider;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.geom2d.Rectangle;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class RectangleColliderComponent extends ColliderComponent {
	
	@EditorData
	private float width, height;
	
	@Override
	protected Shape2D generateShape() {
		return new Rectangle(0, 0, this.width, this.height);
	}
}
