package com.greentree.engine.system;

import java.util.List;

import com.greentree.engine.Cameras;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;


public class ShowColliders extends MultiBehaviour {

	@Override
	protected void start() {
	}

	@Override
	public void update() {
		Color.red.bind();
		Cameras.getMainCamera().translateAsWorld();
		for(ColliderComponent c : getAllComponents(ColliderComponent.class)) {
			List<Point2D> points = c.getPoints();
			float x0 = points.get(0).getX(), y0 = points.get(0).getY();
			for(int i = 1; i < points.size(); i++) {
				float x = points.get(i).getX(), y = points.get(i).getY();
				Graphics.drawLine(x, y, x0, y0);
				x0 = x;
				y0 = y;
			}
		}
		Cameras.getMainCamera().untranslate();
	}

}
