package com.greentree.engine2d.collizion.collider;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.geom2d.Rectangle;
import com.greentree.engine.geom2d.Shape2D;
import com.greentree.engine2d.collizion.ColliderComponent;

/** @author ara */
public final class RectangleColliderComponent extends ColliderComponent {

	@Required
	@EditorData
	private float width, height;

	@Override
	protected Shape2D generateShape() {
		return new Rectangle(0, 0, width, height);
	}
}
