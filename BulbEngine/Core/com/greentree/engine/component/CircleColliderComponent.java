package com.greentree.engine.component;

import com.greentree.engine.component.util.XmlData;
import com.greentree.geom.Circle;
import com.greentree.geom.Shape;

/** @author ara */
public final class CircleColliderComponent extends ColliderComponent {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	@XmlData
	public float radius;
	@XmlData(def = "true")
	private boolean triger;
	
	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public boolean isTriger() {
		return triger;
	}

	@Override
	public void start() {
		shape = new Circle(0, 0, radius);
		super.start();
	}

	@Override
	public void update() {
		shape.moveTo(t.x, t.y);
	}
}
