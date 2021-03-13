package com.greentree.engine.component;

import com.greentree.engine.GameComponent;


/**
 * @author Arseny Latyshev
 *
 */
@RequireComponent(Transform.class)
public abstract class CoordinateGameComponent extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	protected Transform position;
	
	@Override
	protected void awake() {
		position = getComponent(Transform.class);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
}
