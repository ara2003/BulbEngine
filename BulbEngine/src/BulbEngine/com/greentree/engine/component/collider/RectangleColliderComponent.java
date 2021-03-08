package com.greentree.engine.component.collider;

import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Rectangle;

/** @author ara */
public final class RectangleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	private float width, height;
	
	@Override
	public void awake() {
		shape = new Rectangle(0, 0, width, height);
		super.awake();
	}
}
