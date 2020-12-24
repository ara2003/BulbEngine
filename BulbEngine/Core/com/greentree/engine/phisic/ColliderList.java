package com.greentree.engine.phisic;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.geom.Point;
import com.greentree.geom.ShapeList;

/** @author Arseny Latyshev */
public class ColliderList extends ArrayList<ColliderComponent> {
	
	private static final long serialVersionUID = 1L;
	
	/** @param listComponent */
	public ColliderList(final List<ColliderComponent> list) {
		super(list);
	}
	
	public Point getCenter() {
		return getShapeList().getCenter();
	}
	
	private ShapeList getShapeList() {
		final ShapeList list = new ShapeList(size());
		forEach(i->list.add(i.getShape()));
		return list;
	}
}
