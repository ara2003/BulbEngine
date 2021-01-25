package com.greentree.engine.component.collider;

import com.greentree.engine.necessarily;
import com.greentree.engine.component.offsetGameComponent;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.geom.Shape;

@necessarily({ColliderSystem.class})
public abstract class ColliderComponent extends offsetGameComponent {
	
	private static final long serialVersionUID = 1L;
	protected Shape shape;
	
	@Override
	protected void start() {
		super.start();
	}
	
	public float getPenetrationDepth(ColliderComponent other) {
		return shape.getPenetrationDepth(other.getShape());
	}
	
	public final Shape getShape() {
		return shape;
	}
	
	public final void setSize(float width, float height) {
		shape.setSize(width, height);
	}
	
	@Override
	public final void update() {
		shape.moveTo(getX(), getY());
		
	}
	
}
