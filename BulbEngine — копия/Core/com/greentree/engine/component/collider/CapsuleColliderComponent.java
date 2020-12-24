package com.greentree.engine.component.collider;

import com.greentree.engine.component.util.EditorData;
import com.greentree.geom.Capsule;

/** @author ara */
public final class CapsuleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	public float radius, height;
	
	@Override
	public void start() {
		shape = new Capsule(0, 0, height, radius);
	}
	
}
