package com.greentree.math.vector;

public final class float2f extends Vector {
	
	public float2f() {
		super(0, 0);
	}
	
	public float2f(final double x, final double y) {
		super(x, y);
	}
	
	private float2f(final double[] p) {
		super(p);
	}
	
	public float2f addition(final float2f other) {
		return new float2f(coordinates[0] + other.coordinates[0], coordinates[1] + other.coordinates[1]);
	}
	
	public void additionEqual(final double x, final double y) {
		additionEqual(new Vector(x, y));
	}
	
	@Override
	public float2f clone() {
		return new float2f(coordinates);
	}
	
	public float2f getAngel() {
		final float m = size();
		if(m == 0) return this;
		return multiply(1f / m);
	}
	
	@Override
	public float2f getNormal() {
		return scaleTo(1);
	}
	
	public float getX() {
		return (float) coordinates[0];
	}
	
	public float getY() {
		return (float) coordinates[1];
	}
	
	@Override
	public float2f multiply(final double n) {
		final double[] a = VectorUtil.multiply(toArray(), n);
		return new float2f(a[0], a[1]);
	}
	
	@Override
	public float2f multiply(final Matrix m) {
		return new float2f(VectorUtil.multiply(toArray(), m.toArray()));
	}
	
	@Override
	public float2f scaleTo(final float size) {
		return multiply(size / size());
	}

	public float2f subtract(float2f vector) {
		return addition(vector.multiply(-1));
	}
	
	@Override
	public String toString() {
		return "(" + coordinates[0] + " " + coordinates[1] + ")";
	}
	
}
