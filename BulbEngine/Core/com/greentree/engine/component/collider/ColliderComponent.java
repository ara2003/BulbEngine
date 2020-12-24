package com.greentree.engine.component.collider;

import com.greentree.engine.GameComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.object.necessarily;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.geom.Shape;

@necessarily(ColliderSystem.class)
public abstract class ColliderComponent extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	protected Transform position;
	protected Shape shape;
	@EditorData(def = "0")
	private float x, y;
	
	public float getPenetrationDepth(ColliderComponent other) {
		return shape.getPenetrationDepth(other.getShape());
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public final void setSize(float width, float height) {
		shape.setSize(width, height);
	}
	
	@Override
	public final void update() {
		shape.moveTo(position.x + x, position.y + y);
	}
	
}
