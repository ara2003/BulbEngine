package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.joml.Vector2f;

public class Rectangle extends Shape2D {
	
	private float x, y;
	private float width, height;
	private float rotation;
	
	public Rectangle(final float x, final float y, final float width, final float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Rectangle add(Vector2f step) {
		x += step.x();
		y += step.y();
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Rectangle)) return false;
		Rectangle other = (Rectangle) obj;
		if(Float.floatToIntBits(height) != Float.floatToIntBits(other.height)) return false;
		if(Float.floatToIntBits(rotation) != Float.floatToIntBits(other.rotation)) return false;
		if(Float.floatToIntBits(width) != Float.floatToIntBits(other.width)) return false;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) return false;
		return true;
	}
	
	@Override
	public Point2D getCenter() {
		float c = (float) Math.cos(rotation);
		float s = (float) Math.sin(rotation);
		return new Point2D(x + (((width*c) - (height*s))/2), y + (((width*s) + (height*c))/2));
	}
	
	@Override
	public List<Point2D> getPoints() {
		float c = (float) Math.cos(rotation);
		float s = (float) Math.sin(rotation);
		List<Point2D> list = new ArrayList<>();
		list.add(new Point2D(x, y));
		list.add(new Point2D(x + (width * c), y + (width * s)));
		list.add(new Point2D((x + (width*c)) - (height*s), y + (width*s) + (height*c)));
		list.add(new Point2D(x - (height*s), y + (height*c)));
		return list;
	}
	
	@Override
	public boolean isInside(Point2D p) {
		float c = (float) Math.cos(rotation);
		float s = (float) Math.sin(rotation);
		float x0 = p.x - x;
		float y0 = p.y - y;
		float x =  x0 * c + y0 * s;
		float y =  y0 * c - x0 * s;
		if(x < 0)return false;
		if(y < 0)return false;
		if(x > width)return false;
		if(y > height)return false;
		return true;
	}
	
	@Override
	public int getPointsSize() {
		return 4;
	}
	
	@Override
	public float getRadius() {
		return (float) (Math.sqrt((width*width) + (height*height)) / 2);
	}
	
	public float getRotate() {
		return rotation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + Float.floatToIntBits(height);
		result = (prime * result) + Float.floatToIntBits(rotation);
		result = (prime * result) + Float.floatToIntBits(width);
		result = (prime * result) + Float.floatToIntBits(x);
		result = (prime * result) + Float.floatToIntBits(y);
		return result;
	}
	
	@Override
	public void rotate(Point2D point, double ang) {
		rotation += ang;
		Point2D p = new Point2D(x, y);
		p.rotate(point, ang);
		x = p.getX();
		y = p.getY();
	}
	
	
	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Rectangle [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", rotation=" + rotation + "]";
	}
	
	@Override
	public void transleteX(Function<Float, Float> t) {
		x = t.apply(x);
	}
	
	@Override
	public void transleteY(Function<Float, Float> t) {
		y = t.apply(y);
	}

	@Override
	public Shape2D add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
}
