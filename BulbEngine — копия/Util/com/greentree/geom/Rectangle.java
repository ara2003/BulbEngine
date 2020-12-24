package com.greentree.geom;

import java.util.Arrays;
import java.util.List;

import com.greentree.math.vector.float2f;

public class Rectangle extends Shape {
	
	private float x, y;
	private float width, height;
	private float rotation;
	
	public Rectangle(final float x, final float y, final float width, final float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		trim();
	}
	
	@Override
	public void add(float2f step) {
		x += step.getX();
		y += step.getY();
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
	public Point getCenter() {
		float c = (float) Math.cos(rotation);
		float s = (float) Math.sin(rotation);
		return new Point(x + (((width*c) - (height*s))/2), y + (((width*s) + (height*c))/2));
	}
	
	@Override
	public List<Point> getPoints() {
		float c = (float) Math.cos(rotation);
		float s = (float) Math.sin(rotation);
		return Arrays.asList(new Point(x, y), new Point(x + (width * c), y + (width * s)), new Point((x + (width*c)) - (height*s), y + (width*s) + (height*c)), new Point(x - (height*s), y + (height*c)));
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
	public void rotate(Point point, double ang) {
		rotation -= ang;
		Point p = new Point(x, y);
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
	public void transleteX(Translete t) {
		x = t.translete(x);
	}
	
	@Override
	public void transleteY(Translete t) {
		y = t.translete(y);
	}
}
