package com.greentree.engine2d.navmap;


import com.greentree.common.Sized;
import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.engine.geom2d.Capsule;
import com.greentree.engine.geom2d.Circle;
import com.greentree.engine.geom2d.Rectangle;


public class NavMeshObstacle extends StartGameComponent implements Sized {


	@Required
	@EditorData
	private float width, height;
	private Transform poisition;

	public boolean canGo(AbstractVector2f from, AbstractVector2f to, float radius) {
		if(isInside(from, radius))return true;
		return !new Rectangle(getX(), getY(), width, height).isIntersect(new Capsule(from, to, radius));
	}
	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	public float getX() {
		return poisition.position.x() - width/2;
	}

	public float getY() {
		return poisition.position.y() - height/2;
	}
	public boolean isInside(AbstractVector2f vec, float radius) {
		return new Rectangle(getX(), getY(), width, height).isIntersect(new Circle(vec, radius));
	}

	@Override
	public void start() {
		poisition = getComponent(Transform.class);
	}

}
