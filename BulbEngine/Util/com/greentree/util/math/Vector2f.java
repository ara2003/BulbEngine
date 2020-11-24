/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto. The
 * original source remains:
 *
 * Copyright (c) 2006 Erin Catto http://www.gphysics.com
 *
 * This source is provided under the terms of the BSD License.
 *
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *  * Redistributions of source code must retain the above
 *    copyright notice, this list of conditions and the
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above
 *    copyright notice, this list of conditions and the following
 *    disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of
 *    its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package com.greentree.util.math;

import java.io.Serializable;

/** A two dimensional vector
 *
 * @author Kevin Glass */
public strictfp class Vector2f implements ROVector2f, Serializable {

	private static final long serialVersionUID = 1L;
	/** The x component of this vector */
	public float x;
	/** The y component of this vector */
	public float y;

	/** Create an empty vector */
	public Vector2f() {
	}
	
	public Vector2f(final double theta) {
		x = 1.0f;
		y = 0.0f;
		setTheta(theta);
	}
	
	/** Create a new vector
	 *
	 * @param x The x component to assign
	 * @param y The y component to assign */
	public Vector2f(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(final float[] coords) {
		x = coords[0];
		y = coords[1];
	}
	
	/** Create a new vector based on another
	 *
	 * @param other The other vector to copy into this one */
	public Vector2f(final ROVector2f other) {
		this(other.getX(), other.getY());
	}

	public Vector2f(final Vector2f other) {
		this(other.getX(), other.getY());
	}
	
	public strictfp Vector2f add(final double theta) {
		setTheta(getTheta() + theta);
		return this;
	}
	
	/** Add a vector to this vector
	 *
	 * @param v The vector to add */
	public void add(final ROVector2f v) {
		x += v.getX();
		y += v.getY();
	}

	public strictfp Vector2f add(final Vector2f v) {
		x += v.getX();
		y += v.getY();
		return this;
	}
	
	public strictfp Vector2f copy() {
		return new Vector2f(x, y);
	}
	
	/** Get the distance from this point to another
	 *
	 * @param other The other point we're measuring to
	 * @return The distance to the other point */
	@Override
	public float distance(final ROVector2f other) {
		return (float) Math.sqrt(distanceSquared(other));
	}

	public strictfp float distance(final Vector2f other) {
		return (float) Math.sqrt(distanceSquared(other));
	}

	/** Get the distance squared from this point to another
	 *
	 * @param other The other point we're measuring to
	 * @return The distance to the other point */
	@Override
	public float distanceSquared(final ROVector2f other) {
		final float dx = other.getX() - getX();
		final float dy = other.getY() - getY();
		return dx * dx + dy * dy;
	}
	
	public strictfp float distanceSquared(final Vector2f other) {
		final float dx = other.getX() - getX();
		final float dy = other.getY() - getY();
		return dx * dx + dy * dy;
	}
	
	/** @see com.greentree.util.math.ROVector2f#dot(com.greentree.util.math.ROVector2f) */
	@Override
	public float dot(final ROVector2f other) {
		return x * other.getX() + y * other.getY();
	}
	
	public strictfp float dot(final Vector2f other) {
		return x * other.getX() + y * other.getY();
	}
	
	@Override
	public strictfp boolean equals(final Object other) {
		if(other instanceof Vector2f) {
			final Vector2f o = (Vector2f) other;
			return o.x == x && o.y == y;
		}
		return false;
	}
	
	/** Compare two vectors allowing for a (small) error as indicated by the delta.
	 * Note that the delta is used for the vector's components separately, i.e. any
	 * other vector that is contained in the square box with sides 2*delta and this
	 * vector at the center is considered equal.
	 *
	 * @param other The other vector to compare this one to
	 * @param delta The allowed error
	 * @return True iff this vector is equal to other, with a tolerance defined by
	 *         delta */
	public boolean equalsDelta(final ROVector2f other, final float delta) {
		return other.getX() - delta < x && other.getX() + delta > x && other.getY() - delta < y
				&& other.getY() + delta > y;
	}

	public strictfp Vector2f getNormal() {
		final Vector2f cp = copy();
		cp.normalise();
		return cp;
	}
	
	public strictfp Vector2f getPerpendicular() {
		return new Vector2f(-y, x);
	}
	
	public strictfp double getTheta() {
		double theta = StrictMath.toDegrees(StrictMath.atan2(y, x));
		if(theta < -360.0 || theta > 360.0) theta %= 360.0;
		if(theta < 0.0) theta += 360.0;
		return theta;
	}
	
	/** @see com.greentree.util.math.ROVector2f#getX() */
	@Override
	public float getX() {
		return x;
	}
	
	/** @see com.greentree.util.math.ROVector2f#getY() */
	@Override
	public float getY() {
		return y;
	}
	
	@Override
	public strictfp int hashCode() {
		return 997 * (int) x ^ 991 * (int) y;
	}
	
	/** @see com.greentree.util.math.ROVector2f#length() */
	@Override
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	/** The length of the vector squared
	 *
	 * @return The length of the vector squared */
	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	/** Negate this vector
	 *
	 * @return A copy of this vector negated */
	public Vector2f negate() {
		return new Vector2f(-x, -y);
	}
	
	public strictfp Vector2f negateLocal() {
		x = -x;
		y = -y;
		return this;
	}
	
	/** Normalise the vector */
	public void normalise() {
		final float l = length();
		if(l == 0) return;
		x /= l;
		y /= l;
	}
	
	/** Project this vector onto another
	 *
	 * @param b      The vector to project onto
	 * @param result The projected vector */
	@Override
	public void projectOntoUnit(final ROVector2f b, final Vector2f result) {
		final float dp = b.dot(this);
		result.x = dp * b.getX();
		result.y = dp * b.getY();
	}
	
	public strictfp void projectOntoUnit(final Vector2f b, final Vector2f result) {
		final float dp = b.dot(this);
		result.x = dp * b.getX();
		result.y = dp * b.getY();
	}
	
	/** Scale this vector by a value
	 *
	 * @param a The value to scale this vector by */
	public void scale(final float a) {
		x *= a;
		y *= a;
	}

	/** Set the values in this vector
	 *
	 * @param x The x component to set
	 * @param y The y component to set */
	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	/** Set the value of this vector
	 *
	 * @param other The values to set into the vector */
	public void set(final ROVector2f other) {
		set(other.getX(), other.getY());
	}
	
	public strictfp void set(final Vector2f other) {
		this.set(other.getX(), other.getY());
	}
	
	public strictfp void setTheta(double theta) {
		theta %= 360.0;
		if(theta < 0.0) theta += 360.0;
		getTheta();
		final float len = length();
		x = len * (float) FastTrig.cos(StrictMath.toRadians(theta));
		y = len * (float) FastTrig.sin(StrictMath.toRadians(theta));
	}
	
	public strictfp Vector2f sub(final double theta) {
		setTheta(getTheta() - theta);
		return this;
	}
	
	/** Subtract a vector from this vector
	 *
	 * @param v The vector subtract */
	public void sub(final ROVector2f v) {
		x -= v.getX();
		y -= v.getY();
	}
	
	public strictfp Vector2f sub(final Vector2f v) {
		x -= v.getX();
		y -= v.getY();
		return this;
	}

	/** @see java.lang.Object#toString() */
	@Override
	public strictfp String toString() {
		return "[Vector2f " + x + "," + y + " (" + length() + ")]";
	}
}
