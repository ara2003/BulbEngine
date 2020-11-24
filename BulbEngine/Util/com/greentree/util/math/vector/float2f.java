package com.greentree.util.math.vector;

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

	public void additionEqual(final double x, final double y) {
		additionEqual(new Vector(x, y));
	}

	public float2f getAngel() {
		final float m = module();
		if(m == 0) return this;
		return multiply(1f / m);
	}

	public float getX() {
		return (float) list[0];
	}
	
	public float getY() {
		return (float) list[1];
	}

	@Override
	public float2f multiply(final double n) {
		final double[] a = MathUtil.multiply(toArray(), n);
		return new float2f(a[0], a[1]);
	}

	@Override
	public float2f multiply(final Matrix m) {
		return new float2f(MathUtil.multiply(toArray(), m.toArray()));
	}

	@Override
	public String toString() {
		return "(" + list[0] + " " + list[1] + ")";
	}
}
