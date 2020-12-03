package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

import com.greentree.util.math.vector.float2f;

public final class GeomUtil {
	
	public static Point contact(final Line a, final Line b, final boolean border) {
		final float a1 = a.getY1() - a.getY2();
		final float a2 = b.getY1() - b.getY2();
		final float b1 = a.getX2() - a.getX1();
		final float b2 = b.getX2() - b.getX1();
		final float z = a1 * b2 - a2 * b1;
		if(z == 0) return null;
		final float x = -(a2 * b.getX1() * b1 + b2 * b.getY1() * b1 - (a1 * a.getX1() + b1 * a.getY1()) * b2) / z;
		final float y = (a2 * b.getX1() * a1 + b2 * b.getY1() * a1 - (a1 * a.getX1() + b1 * a.getY1()) * a2) / z;
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
	
	public static Point getCenter(final List<Point> point) {
		float x = 0, y = 0;
		for(final Point p : point) {
			x += p.getX();
			y += p.getY();
		}
		return new Point(x / point.size(), y / point.size());
	}
	
	public static Point[] getPoint(final float... point) {
		final Point[] res = new Point[point.length / 2];
		for(int i = 0; i < point.length - point.length % 2; i += 2) res[i / 2] = new Point(point[i], point[i + 1]);
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
	
	public static Point rey(final Point point, final float2f v, final float maxDistanse, final List<Shape> world) {
		if(v.module() <= 0) return null;
		v.multiplyEqual(1f / v.module());
		final Line scanner = new Line(point.getX(), point.getY(), point.getX() + v.getX() * maxDistanse,
				point.getY() + v.getY() * maxDistanse);
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

	public static Point reyCast(final Point point, final float2f v, float maxDistanse, final List<Shape> world) {
		if(v.module() == 0) return null;
		v.multiplyEqual(1f / v.module());
		final Point p = new Point(point.getX(), point.getY());
		float dis = 0;
		int t = 50;
		while(t-- > 0 && maxDistanse > 0) {
			dis = GeomUtil.distanse(p, world) * 0.99f;
			maxDistanse -= dis;
			p.add(v.multiply(dis));
			if(dis < 1) return p;
		}
		return null;
	}
	
	public static List<Line> toLine(final List<Point> points) {
		final List<Line> res = new ArrayList<>();
		for(int i = 1; i < points.size(); i++) res.add(new Line(points.get(i - 1), points.get(i)));
		res.set(0, new Line(points.get(0), points.get(points.size() - 1)));
		return res;
	}
	
	private GeomUtil() {
	}
}
