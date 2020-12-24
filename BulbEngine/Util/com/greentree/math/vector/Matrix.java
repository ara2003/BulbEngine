package com.greentree.math.vector;

import com.greentree.math.MathException;

public class Matrix {
	
	double[][] list;
	
	public Matrix(final double[][] d) {
		list = d;
	}
	
	public Matrix(final Vector... v) {
		int mxlen = v[0].dimension();
		for(int i = 1; i < v.length; i++) mxlen = Math.max(mxlen, v[i].dimension());
		list = new double[mxlen][v.length];
		for(int i = 0; i < list.length; i++)
			for(int j = 0; j < list[i].length; j++) list[i][j] = i < v[j].dimension() ? v[j].toArray()[i] : 0;
	}
	
	/**
	 * @param angle
	 * @return 2d rotation matrix
	 */
	public static Matrix getRotationMatrix(double angle) {
		final float c = (float) Math.cos(angle);
		final float s = (float) Math.sin(angle);
		return new Matrix(new double[][] {{c, -s}, {s, c}});
	}
	
	public void clear() {
		for(int i = 0; i < list.length; i++) list[i] = new double[list[i].length];
	}
	
	public double det() {
		return VectorUtil.det(toArray());
	}
	
	public Matrix inverse() throws MathException {
		return new Matrix(VectorUtil.inverse(toArray()));
	}
	
	public void inverseEqual() throws MathException {
		set(inverse().toArray());
	}
	
	public Matrix multiply(final double n) throws MathException {
		return new Matrix(VectorUtil.multiply(toArray(), n));
	}
	
	public Matrix multiply(final Matrix m) throws MathException {
		return new Matrix(VectorUtil.multiply(toArray(), m.toArray()));
	}
	
	public void multiplyEqual(final double m) throws MathException {
		set(multiply(m));
	}
	
	public void multiplyEqual(final Matrix m) throws MathException {
		set(VectorUtil.multiply(toArray(), m.toArray()));
	}
	
	public Matrix pow(final int i) throws MathException {
		return new Matrix(VectorUtil.pow(toArray(), i));
	}
	
	public void powEqual(final int i) throws MathException {
		set(pow(i).toArray());
	}
	
	public void set(final double[][] m) {
		list = m;
	}
	
	public void set(final Matrix m) {
		set(m.toArray());
	}
	
	/** @return return clone of array */
	public double[][] toArray() {
		final double[][] a = new double[list.length][];
		for(int i = 0; i < a.length; i++) {
			a[i] = new double[list[i].length];
			for(int j = 0; j < a[i].length; j++) a[i][j] = list[i][j];
		}
		return a;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(final double[] element : list) {
			s += "[[";
			for(int j = 0; j < element.length; j++) s += (j == 0 ? "" : ", ") + element[j];
			s += "]]" + (element != list[list.length - 1] ? "\n" : "");
		}
		return s;
	}
}
