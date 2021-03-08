package com.greentree.engine.component.collider;

import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Capsule;

/** @author ara */
public final class CapsuleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	public float radius, height;
	
	@Override
	public void awake() {
		shape = new Capsule(0, 0, height, radius);
		super.awake();
	}
}
