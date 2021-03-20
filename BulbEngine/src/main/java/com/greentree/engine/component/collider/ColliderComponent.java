package com.greentree.engine.component.collider;

import com.greentree.engine.component.GameComponent;
import com.greentree.engine.component.RequireComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.geom2d.Shape2D;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.engine.system.NecessarilySystems;

@NecessarilySystems({ColliderSystem.class})
@RequireComponent({Transform.class})
public abstract class ColliderComponent extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private Shape2D shape;
	private Transform position;
	
	@Override
	protected final void awake() {
		this.shape    = this.generateShape();
	}
	
	protected abstract Shape2D generateShape();
	
	public final Shape2D getShape() {
		return this.shape;
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public final void setPosition(final float x, final float y) {
		this.shape.moveTo(x, y);
	}
	
	public final void setSize(final float width, final float height) {
		this.shape.setSize(width, height);
	}
	
	@Override
	protected final void start() {
		this.position = getComponent(Transform.class);
		this.updatePosition();
	}
	
	@Override
	public final void update() {
		this.updatePosition();
	}
	
	public final void updatePosition() {
		this.setPosition(this.getX(), this.getY());
	}
}
