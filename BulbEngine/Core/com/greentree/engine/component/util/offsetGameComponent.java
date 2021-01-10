package com.greentree.engine.component.util;

import com.greentree.engine.GameComponent;
import com.greentree.engine.RequireComponent;
import com.greentree.engine.component.Transform;
import com.greentree.math.vector.float2f;


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

	public float getX(){
		return position.x + offsetX;
	}
	
	public float getY(){
		return position.y + offsetY;
	}

	public float2f getOffsetVector() {
		return new float2f(offsetX, offsetY);
	}
}
