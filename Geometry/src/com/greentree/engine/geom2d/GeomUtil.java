package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

public final class GeomUtil {
	
	public static Point2D contact(final Line a, final Line b, final boolean border) {
		final float a1 = a.getY1() - a.getY2();
		final float a2 = b.getY1() - b.getY2();
		final float b1 = a.getX2() - a.getX1();
		final float b2 = b.getX2() - b.getX1();
		final float z = (a1 * b2) - (a2 * b1);
		if(z == 0) return null;
		final float x = -(((a2 * b.getX1() * b1) + (b2 * b.getY1() * b1)) - (((a1 * a.getX1()) + (b1 * a.getY1())) * b2)) / z;
		final float y = (((a2 * b.getX1() * a1) + (b2 * b.getY1() * a1)) - (((a1 * a.getX1()) + (b1 * a.getY1())) * a2)) / z;
		if(border) {
			if(x < Math.max(Math.min(a.getX1(), a.getX2()), Math.min(b.getX1(), b.getX2()))) return null;
			if(x > Math.min(Math.max(a.getX1(), a.getX2()), Math.max(b.getX1(), b.getX2()))) return null;
			if(y < Math.max(Math.min(a.getY1(), a.getY2()), Math.min(b.getY1(), b.getY2()))) return null;
			if(y > Math.min(Math.max(a.getY1(), a.getY2()), Math.max(b.getY1(), b.getY2()))) return null;
		}
		return new Point2D(x, y);
	}
	
	public static Point2D contactLine(final Line line, final Line line_no_border) {
		final Point2D p = GeomUtil.contact(line, line_no_border, false);
		if(p.getX() > Math.max(line.getX1(), line.getX2())) return null;
		if(p.getX() < Math.min(line.getX1(), line.getX2())) return null;
		if(p.getY() > Math.max(line.getY1(), line.getY2())) return null;
		if(p.getY() < Math.min(line.getY1(), line.getY2())) return null;
		return p;
	}
	
	public static float distanse(final Point2D p, final List<Shape2D> a) {
		float dis = Float.MAX_VALUE;
		for(final Shape2D l : a) dis = Math.min(dis, l.distanse(p));
		return dis;
	}
	
	public static Point2D getCenter(final List<? extends Shape2D> world) {
		final List<Point2D> list = new ArrayList<>();
		for(final Shape2D shape : world) list.addAll(shape.getPoints());
		
		float x = 0, y = 0;
		for(final Point2D p : list) {
			x += p.getX();
			y += p.getY();
		}
		return new Point2D(x / list.size(), y / list.size());
	}
	
	public static Point2D getMassCenter(Shape2D shape) {
		List<Point2D> points = shape.getPoints();
		float x = 0, y = 0;
		int n = 0;
		for(Point2D a : points)
			for(Point2D b : points)if(a != b)
				for(Point2D c : points) if((a!=c)&&(b!=c)) {
					x += (a.getX() + b.getX() + c.getX()) / 3;
					y += (a.getY() + b.getY() + c.getY()) / 3;
					n++;
				}
		return new Point2D(x / n, y / n);
	}
	
	public static Point2D[] getPoint(final float... point) {
		final Point2D[] res = new Point2D[point.length / 2];
		for(int i = 0; i < (point.length - (point.length % 2)); i += 2) res[i / 2] = new Point2D(point[i], point[i + 1]);
		return res;
	}
	
	public static Point2D minPoint(final Point2D point, final List<Shape2D> world) {
		final List<Point2D> con = new ArrayList<>();
		for(final Shape2D s : world) {
			final Point2D p = s.minPoint(point);
			if(p != null) con.add(p);
		}
		float dis0 = Float.MAX_VALUE, dis;
		Point2D res = null;
		for(final Point2D p : con) {
			dis = point.distanse(p);
			if(dis0 > dis) {
				dis0 = dis;
				res = p;
			}
		}
		return res;
	}
	
	public static Point2D rey(final Point2D point, @SuppressWarnings("exports") final Vector2f vec, final float maxDistanse, final List<Shape2D> world) {
		if(vec.length() <= 0) return null;
		vec.mul(1f / vec.length());
		final Line scanner = new Line(point.getX(), point.getY(), point.getX() + (vec.x * maxDistanse),
				point.getY() + (vec.y * maxDistanse));
		final List<Point2D> points = new ArrayList<>();
		for(final Shape2D s : world) points.addAll(scanner.contact(s));
		float dis0 = maxDistanse, dis;
		Point2D res = null;
		for(final Point2D p : points) {
			dis = point.distanse(p);
			if(dis0 > dis) {
				dis0 = dis;
				res = p;
			}
		}
		return res;
	}
	
	public static Point2D reyCast(final Point2D point, @SuppressWarnings("exports") final Vector2f vec, float maxDistanse, final List<Shape2D> world) {
		if(vec.length() == 0) return null;
		vec.mul(1f / vec.length());
		final Point2D p = new Point2D(point.getX(), point.getY());
		float dis = 0;
		int t = 50;
		while((t-- > 0) && (maxDistanse > 0)) {
			dis = GeomUtil.distanse(p, world) * 0.99f;
			maxDistanse -= dis;
			p.add(vec.mul(dis));
			if(dis < 1) return p;
		}
		return null;
	}
	
	public static void rotate(final List<Shape2D> world, final Point2D rotationCenter, final double angle) {
		for(final Shape2D shape : world) shape.rotate(rotationCenter, angle);
	}
	
	public static List<Line> toLine(final List<Point2D> points) {
		final List<Line> res = new ArrayList<>();
		for(int i = 1; i < points.size(); i++) res.add(new Line(points.get(i - 1), points.get(i)));
		res.set(0, new Line(points.get(0), points.get(points.size() - 1)));
		return res;
	}
}
