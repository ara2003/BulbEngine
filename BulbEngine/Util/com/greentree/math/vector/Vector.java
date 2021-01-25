package com.greentree.math.vector;

import com.greentree.math.MathException;

public class Vector {
	
	protected double[] coordinates;
	
	public Vector() {
	}
	
	public Vector(final double... fs) {
		coordinates = fs;
	}
	
	public Vector addition(final Vector b) {
		return new Vector(VectorUtil.addition(toArray(), b.toArray()));
	}
	
	public void additionEqual(final Vector b) {
		coordinates = VectorUtil.addition(coordinates, b.toArray());
	}
	
	@Override
	public Vector clone() {
		return new Vector(coordinates);
	}
	
	public int dimension() {
		return coordinates.length;
	}
	
	public double getArea(final Vector v) {
		return new Matrix(this, v).det();
	}
	
	public Vector getNormal() {
		return scaleTo(1);
	}
	
	public Vector multiply(final double n) {
		return new Vector(VectorUtil.multiply(toArray(), n));
	}
	
	public Vector multiply(final Matrix m) {
		return new Vector(VectorUtil.multiply(toArray(), m.toArray()));
	}
	
	public Vector multiply(final Vector v) throws MathException {
		return new Vector(VectorUtil.multiply(toArray(), v.toArray()));
	}
	
	public void multiplyEqual(final double f) {
		coordinates = VectorUtil.multiply(toArray(), f);
	}
	
	public void multiplyEqual(final Matrix m) {
		coordinates = VectorUtil.multiply(coordinates, m.toArray());
	}
	
	public void multiplyEqual(final Vector f) throws MathException {
		coordinates = VectorUtil.multiply(coordinates, f.toArray());
	}
	
	public double scalarMultiply(final double[] b) {
		return VectorUtil.scalarMultiply(toArray(), b);
	}
	
	public double scalarMultiply(final Vector v) {
		return scalarMultiply(v.toArray());
	}
	
	public Vector scaleTo(final float size) {
		return multiply(size / size());
	}
	
	public void setNullSize() {
		coordinates = new double[coordinates.length];
	}
	
	public float size() {
		double res = 0;
		for(final double n : coordinates) res += n * n;
		return (float) Math.sqrt(res);
	}
	
	/** @return return clone of array */
	public double[] toArray() {
		final double[] c = new double[coordinates.length];
		for(int i = 0; i < c.length; i++) c[i] = coordinates[i];
		return c;
	}
	
	@Override
	public String toString() {
		String s = "[ ";
		for(final double f : coordinates) s += f + " ";
		return s + "]";
	}
	
	public Vector subtract(Vector vector) {
		return addition(vector.multiply(-1));
	}
	
}
