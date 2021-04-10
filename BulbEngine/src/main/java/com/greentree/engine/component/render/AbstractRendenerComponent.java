package com.greentree.engine.component.render;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.Renderable;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.UpdatingGameComponent;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public abstract class AbstractRendenerComponent extends UpdatingGameComponent implements Renderable {
	
	protected static final GraphicsI GL = BulbGL.getGraphics();
	protected Transform position;
	
	@Override
	protected void start() {
		this.position = this.getComponent(Transform.class);
	}
	
	@Override
	public void update() {
	}
	
}
