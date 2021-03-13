package com.greentree.engine.component;

import org.joml.Vector2f;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public abstract class offsetGameComponent extends CoordinateGameComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData(name = "x")
	private float offsetX;
	@EditorData(name = "y")
	private float offsetY;
	
	public Vector2f getOffsetVector() {
		return new Vector2f(offsetX, offsetY);
	}
	
	@Override
	public float getX() {
		return position.x + offsetX;
	}
	
	@Override
	public float getY() {
		return position.y + offsetY;
	}
}
