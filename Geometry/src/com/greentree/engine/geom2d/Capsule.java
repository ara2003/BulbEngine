package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;

import com.greentree.engine.geom2d.util.Geom2DUtil;

/** @author Arseny Latyshev
 *
 * @see Shape2D */
public class Capsule extends Shape2D {

	protected final static int PointInCapsule = Circle.PointInCircle;
	private final Point2D focus1, focus2;
	private final float radius;

	public Capsule(final float focusX, final float focusY, final float height, final float radius) {
		this(focusX, focusY, focusX, focusY + height, radius);
	}


	public Capsule(final float x1, final float y1, final float x2, final float y2, final float radius) {
		if(radius <= 0)throw new IllegalArgumentException("radius must be positive : " + radius);
		focus1 = new Point2D(x1, y1);
		focus2 = new Point2D(x2, y2);
		this.radius = radius;
	}


	public Capsule(Vector2f p1, Vector2f p2, float radius) {
		if(radius <= 0)throw new IllegalArgumentException("radius must be positive : " + radius);
		focus1 = new Point2D(p1);
		focus2 = new Point2D(p2);
		this.radius = radius;
	}


	@Override
	public Capsule add(final Vector2f step) {
		focus1.add(step);
		focus2.add(step);
		return this;
	}

	@Override
	public Point2D getCenter() {
		return Geom2DUtil.getCenter(Arrays.asList(focus1, focus2));
	}

	public Point2D getFocus1() {
		return focus1.clone();
	}

	@Override
	public List<Point2D> getPoints() {
		final Vector2f focus_vec = focus2.getRadiusVector().sub(focus1.getRadiusVector());// 1 -> 2
		if(focus_vec.length() == 0)focus_vec.set(1, 0);
		focus_vec.mul(new Matrix2f().rotate((float) (Math.PI / 2))).normalize(radius);
		final List<Point2D> points = new ArrayList<>(Capsule.PointInCapsule);
		final Matrix2f mat = new Matrix2f().rotate((float) (2 * Math.PI / Capsule.PointInCapsule));
		for(int i = 0; i < Math.ceil(PointInCapsule / 2f); i++) {
			focus_vec.mul(mat);
			points.add(new Point2D(focus1, focus_vec));
		}
		for(int i = 0; i < Math.floor(Capsule.PointInCapsule / 2f); i++) {
			focus_vec.mul(mat);
			points.add(new Point2D(focus2, focus_vec));
		}
		return points;
	}

	//	@Override
	//	public boolean isInside(Point2D p) {
	//		if(focus1.distanseSqr(p) < radius*radius)return true;
	//		if(focus2.distanseSqr(p) < radius*radius)return true;
	//		final Vector2f focus_vec = focus2.getRadiusVector().sub(focus1.getRadiusVector());// 1 -> 2
	//		final Vector2f vec = new Vector2f();// 1 -> 2
	//		focus_vec.mul(new Matrix2f().rotate((float) (Math.PI / 2)), vec).normalize(radius);
	//		Rectangle r = new Rectangle(focus2.x - vec.x, focus2.y - vec.y, focus_vec.length(), 2*radius);
	//		return r.isInside(p);
	//	}

	@Override
	public int getPointsSize() {
		return PointInCapsule;
	}

	@Override
	public float getRadius() {
		return radius + focus1.distanse(focus2) / 2;
	}

	@Override
	public void rotate(final Point2D point, final double ang) {
		focus1.rotate(point, ang);
		focus2.rotate(point, ang);
	}

	@Override
	public String toString() {
		return "Capsule [focus1=" + focus1 + ", focus2=" + focus2 + ", radius=" + radius + "]";
	}

}
