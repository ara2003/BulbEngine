package com.greentree.util.math.vector;

import com.greentree.util.math.MathException;

public class Vector {

	protected double[] list;
	
	public Vector() {
	}
	
	public Vector(final double... fs) {
		list = fs;
	}
	
	public Vector addition(final Vector b) {
		return new Vector(MathUtil.addition(toArray(), b.toArray()));
	}
	
	public void additionEqual(final Vector b) {
		list = MathUtil.addition(list, b.toArray());
	}
	
	public void clear() {
		list = new double[list.length];
	}
	
	public double getArea(final Vector v) {
		return new Matrix(this, v).det();
	}

	public float module() {
		double res = 0;
		for(final double n : list) res += n * n;
		return (float) Math.sqrt(res);
	}

	public Vector multiply(final double n) {
		return new Vector(MathUtil.multiply(toArray(), n));
	}

	public Vector multiply(final Matrix m) {
		return new Vector(MathUtil.multiply(toArray(), m.toArray()));
	}

	public Vector multiply(final Vector v) throws MathException {
		return new Vector(MathUtil.multiply(toArray(), v.toArray()));
	}

	public void multiplyEqual(final double f) {
		list = MathUtil.multiply(toArray(), f);
	}

	public void multiplyEqual(final Matrix m) {
		list = MathUtil.multiply(list, m.toArray());
	}

	public void multiplyEqual(final Vector f) throws MathException {
		list = MathUtil.multiply(list, f.toArray());
	}

	public double scalarMultiply(final double[] b) {
		return MathUtil.scalarMultiply(toArray(), b);
	}

	public double scalarMultiply(final Vector v) {
		return scalarMultiply(v.toArray());
	}
	
	public int size() {
		return list.length;
	}
	
	/** @return return clone of array */
	public double[] toArray() {
		final double[] c = new double[list.length];
		for(int i = 0; i < c.length; i++) c[i] = list[i];
		return c;
	}
	
	@Override
	public String toString() {
		String s = "[ ";
		for(final double f : list) s += f + " ";
		return s + "]";
	}
}
