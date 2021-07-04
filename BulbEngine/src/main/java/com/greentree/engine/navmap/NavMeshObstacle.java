package com.greentree.engine.navmap;


import org.joml.Vector2f;

import com.greentree.common.Sized;
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

	public boolean canGo(Vector2f from, Vector2f to, float radius) {
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
		return poisition.x() - width/2;
	}

	public float getY() {
		return poisition.y() - height/2;
	}
	public boolean isInside(Vector2f vec, float radius) {
		return new Rectangle(getX(), getY(), width, height).isIntersect(new Circle(vec, radius));
	}

	@Override
	public void start() {
		poisition = getComponent(Transform.class);
	}

}
