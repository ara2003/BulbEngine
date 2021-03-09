package com.greentree.engine.component.collider;

import com.greentree.engine.necessarilySystems;
import com.greentree.engine.component.offsetGameComponent;
import com.greentree.engine.geom2d.Shape2D;
import com.greentree.engine.system.ColliderSystem;

@necessarilySystems({ColliderSystem.class})
public abstract class ColliderComponent extends offsetGameComponent {
	
	private static final long serialVersionUID = 1L;
	protected Shape2D shape;
	
	public final Shape2D getShape() {
		return shape;
	}
	
	public final void setSize(float width, float height) {
		shape.setSize(width, height);
	}
	
	@Override
	protected void start() {
		super.start();
	}
	
	@Override
	public final void update() {
		shape.moveTo(getX(), getY());
	}
}
