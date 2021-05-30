package com.greentree.common;

/** @author Arseny Latyshev */
public interface Positioned {

	default void add(final float x, final float y) {
		set(getX() + x, getY() + y);
	}

	default void addX(final float f) {
		setX(f + getX());
	}

	default void addY(final float f) {
		setX(f + getY());
	}

	float getX();
	float getY();

	default Positioned mul(final float i) {
		setX(i * getX());
		setY(i * getY());
		return this;
	}

	default void set(final float x, final float y) {
		setX(x);
		setY(y);
	}

	void setX(float f);
	void setY(float f);

	default Positioned sub(final Positioned p) {
		setX(getX() - p.getX());
		setY(getY() - p.getY());
		return this;
	}
}
