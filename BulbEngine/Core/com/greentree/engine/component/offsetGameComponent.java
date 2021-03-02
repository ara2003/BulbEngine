package com.greentree.engine.component;

import org.joml.Vector2f;

import com.greentree.engine.GameComponent;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.RequireComponent;


/**
 * @author Arseny Latyshev
 *
 */
@RequireComponent({Transform.class})
public class offsetGameComponent extends GameComponent {
	private static final long serialVersionUID = 1L;
	
	private Transform position;
	@EditorData(name="x")
	private float offsetX;
	@EditorData(name="y")
	private float offsetY;
	
	public Vector2f getOffsetVector() {
		return new Vector2f(offsetX, offsetY);
	}
	
	public float getX(){
		return position.x + offsetX;
	}
	
	public float getY(){
		return position.y + offsetY;
	}
	
	@Override
	protected void start() {
		position = getComponent(Transform.class);
	}
}
