package com.greentree.engine.collizion;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.ShapeList;

/** @author Arseny Latyshev */
public class ColliderList extends ArrayList<ColliderComponent> {
	
	private static final long serialVersionUID = 1L;
	
	/** @param listComponent */
	public ColliderList(final List<ColliderComponent> list) {
		super(list);
	}
	
	public Point2D getCenter() {
		return this.getShapeList().getCenter();
	}
	
	private ShapeList getShapeList() {
		final ShapeList list = new ShapeList(this.size());
		this.forEach(i->list.add(i.getShape()));
		return list;
	}
}
