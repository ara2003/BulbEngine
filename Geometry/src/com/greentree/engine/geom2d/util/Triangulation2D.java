package com.greentree.engine.geom2d.util;


import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.Rectangle;
import com.greentree.engine.geom2d.Shape2D;

public abstract class Triangulation2D {

	
	private static class Triangulation2DI {
		private final List<Point2D> points;
		
		public Triangulation2DI(List<Point2D> points) {
			this.points = new ArrayList<>(points.size() * 3);
			for(int i = 1; i < points.size()-1; i++) {
				Point2D a = points.get(i - 1);
				Point2D b = points.get(i - 1);
				Point2D c = points.get(i - 1);
				
			}
		}

		public List<Point2D> get() {
			return points;
		}

	}

	public static void main(String[] args) {
		Shape2D rect = new Rectangle(0, 0, 1, 1);

		System.out.println(triangulation(rect));
	}

	public static List<Point2D> triangulation(Shape2D s) {
		return triangulation(s.getPoints());
	}

	public static List<Point2D> triangulation(List<Point2D> points) {
		return new Triangulation2DI(points).get();
	}

}
