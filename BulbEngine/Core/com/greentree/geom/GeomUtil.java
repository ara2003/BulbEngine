package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class GeomUtil {
	
	public static Point contact(final Line a, final Line b, final boolean border) {
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
		return new Point(x, y);
	}
	
	public static Point contactLine(final Line line, final Line line_no_border) {
		final Point p = GeomUtil.contact(line, line_no_border, false);
		if(p.getX() > Math.max(line.getX1(), line.getX2())) return null;
		if(p.getX() < Math.min(line.getX1(), line.getX2())) return null;
		if(p.getY() > Math.max(line.getY1(), line.getY2())) return null;
		if(p.getY() < Math.min(line.getY1(), line.getY2())) return null;
		return p;
	}
	
	public static float distanse(final Point p, final List<Shape> a) {
		float dis = Float.MAX_VALUE;
		for(final Shape l : a) dis = Math.min(dis, l.distanse(p));
		return dis;
	}
	
	public static Point getCenter(final List<? extends Shape> world) {
		final List<Point> list = new ArrayList<>();
		for(final Shape shape : world) list.addAll(shape.getPoints());
		
		float x = 0, y = 0;
		for(final Point p : list) {
			x += p.getX();
			y += p.getY();
		}
		return new Point(x / list.size(), y / list.size());
	}
	
	public static Point getMassCenter(Shape shape) {
		List<Point> points = shape.getPoints();
		float x = 0, y = 0;
		int n = 0;
		for(Point a : points)
			for(Point b : points)if(a != b)
				for(Point c : points) if((a!=c)&&(b!=c)) {
					x += (a.getX() + b.getX() + c.getX()) / 3;
					y += (a.getY() + b.getY() + c.getY()) / 3;
					n++;
				}
		return new Point(x / n, y / n);
	}
	
	public static Point[] getPoint(final float... point) {
		final Point[] res = new Point[point.length / 2];
		for(int i = 0; i < (point.length - (point.length % 2)); i += 2) res[i / 2] = new Point(point[i], point[i + 1]);
		return res;
	}
	
	public static Point minPoint(final Point point, final List<Shape> world) {
		final List<Point> con = new ArrayList<>();
		for(final Shape s : world) {
			final Point p = s.minPoint(point);
			if(p != null) con.add(p);
		}
		float dis0 = Float.MAX_VALUE, dis;
		Point res = null;
		for(final Point p : con) {
			dis = point.distanse(p);
			if(dis0 > dis) {
				dis0 = dis;
				res = p;
			}
		}
		return res;
	}
	
	public static Point rey(final Point point, final Vector2f v, final float maxDistanse, final List<Shape> world) {
		if(v.length() <= 0) return null;
		v.normalize();
		final Line scanner = new Line(point.getX(), point.getY(), point.getX() + (v.x * maxDistanse),
				point.getY() + (v.y * maxDistanse));
		final List<Point> points = new ArrayList<>();
		for(final Shape s : world) points.addAll(scanner.contact(s));
		float dis0 = maxDistanse, dis;
		Point res = null;
		for(final Point p : points) {
			dis = point.distanse(p);
			if(dis0 > dis) {
				dis0 = dis;
				res = p;
			}
		}
		return res;
	}
	
	public static Point reyCast(final Point point, final Vector2f v, float maxDistanse, final List<Shape> world) {
		if(v.length() <= 0) return null;
		v.normalize();
		final Point p = new Point(point.getX(), point.getY());
		float dis = 0;
		int t = 50;
		while((t-- > 0) && (maxDistanse > 0)) {
			dis = GeomUtil.distanse(p, world) * 0.99f;
			maxDistanse -= dis;
			p.add(v.mul(dis));
			if(dis < 1) return p;
		}
		return null;
	}
	
	public static void rotate(final List<Shape> world, final Point rotationCenter, final double angle) {
		for(final Shape shape : world) shape.rotate(rotationCenter, angle);
	}
	
	public static List<Line> toLine(final List<Point> points) {
		final List<Line> res = new ArrayList<>();
		for(int i = 1; i < points.size(); i++) res.add(new Line(points.get(i - 1), points.get(i)));
		res.set(0, new Line(points.get(0), points.get(points.size() - 1)));
		return res;
	}
}
