package com.greentree.geom;

public class Rect extends Poligon {

	public Rect(final float x, final float y, final float w, final float h) {
		super(x, y, x + w, y, x + w, y + h, x, y + h);
	}

	public Rect(final Point p1, final Point p2) {
		super(p1.getX(), p1.getY(), p2.getX(), p1.getY(), p2.getX(), p2.getY(), p1.getX(), p2.getY(), p1.getX(),
				p1.getY());
	}

	private float getHeight() {
		return point[2].getY() - point[0].getY();
	}
	
	private float getWidth() {
		return point[2].getX() - point[0].getX();
	}
	
	public float getX() {
		return point[0].getY();
	}
	
	public float getY() {
		return point[0].getX();
	}
	
	@Override
	public String toString() {
		return "Rect: " + getX() + " " + getY() + " " + getWidth() + " " + getHeight();
	}
}
