package com.greentree.engine.component;

import com.greentree.engine.collider.CircleCollider;
import com.greentree.engine.collider.Collider;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.XmlData;

/** @author ara */
public final class CircleColliderComponent extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	public Collider collider;
	@XmlData
	public float radius;
	private Transform t;
	@XmlData(def = "true")
	public boolean triger;
	@XmlData(name = "typeCollider")
	public String type;

	@Override
	public void start() {
		collider = new CircleCollider(radius, triger);
		scene.addCollider(collider, object);
	}
	
	@Override
	public void update() {
		collider.moveTo(t.x, t.y);
	}
}
