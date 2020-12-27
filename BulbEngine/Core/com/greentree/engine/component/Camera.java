package com.greentree.engine.component;

import com.greentree.engine.component.util.DiapasonInt;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.object.RequireComponent;


/**
 * @author Arseny Latyshev
 *
 */
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	private static final long serialVersionUID = 1L;
	
	@EditorData
	@DiapasonInt(min = 1)
	private int width, height;
	private Transform position;
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getX() {
		return (int) position.x;
	}
	
	public int getY() {
		return (int) position.y;
	}
	
	public boolean mustDraw(RendenerComponent rendener) {//TODO
		return true;
	}
	
	@Override
	protected void start() {
		
	}
	
	@Override
	public void update() {
		
	}
	
	
}
