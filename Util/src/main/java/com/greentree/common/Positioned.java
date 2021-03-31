package com.greentree.common;

/** @author Arseny Latyshev */
public interface Positioned {
	
	default void add(final float x, final float y) {
		this.set(this.getX() + x, this.getY() + y);
	}
	
	default void addX(final float f) {
		this.setX(f + this.getX());
	}
	
	default void addY(final float f) {
		this.setX(f + this.getY());
	}
	
	float getX();
	float getY();
	
	default Positioned mul(final float i) {
		this.setX(i * this.getX());
		this.setY(i * this.getY());
		return this;
	}
	
	default void set(final float x, final float y) {
		this.setX(x);
		this.setY(y);
	}
	
	void setX(float f);
	void setY(float f);
	
	default Positioned sub(final Positioned p) {
		this.setX(this.getX() - p.getX());
		this.setY(this.getY() - p.getY());
		return this;
	}
}
