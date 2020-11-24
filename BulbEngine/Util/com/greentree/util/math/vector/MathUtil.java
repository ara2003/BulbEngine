package com.greentree.util.math.vector;

import com.greentree.util.math.MathException;
import com.greentree.util.math.Matrix2f;
import com.greentree.util.math.ROVector2f;
import com.greentree.util.math.Vector2f;

public class MathUtil {

	/** Prevent construction */
	private MathUtil() {
	}
	
	/** Create the absolute version of a matrix
	 *
	 * @param A The matrix to make absolute
	 * @return A newly created absolute matrix */
	public static Matrix2f abs(final Matrix2f A) {
		return new Matrix2f(MathUtil.abs(A.col1), MathUtil.abs(A.col2));
	}

	/** Make a vector absolute
	 *
	 * @param a The vector to make absolute
	 * @return A newly created result vector */
	public static Vector2f abs(final Vector2f a) {
		return new Vector2f(Math.abs(a.x), Math.abs(a.y));
	}

	/** Add two matricies
	 *
	 * @param A The first matrix
	 * @param B The second matrix
	 * @return A newly created matrix containing the result */
	public static Matrix2f add(final Matrix2f A, final Matrix2f B) {
		final Vector2f temp1 = new Vector2f(A.col1);
		temp1.add(B.col1);
		final Vector2f temp2 = new Vector2f(A.col2);
		temp2.add(B.col2);
		return new Matrix2f(temp1, temp2);
	}

	public static double[] addition(final double[] a, final double[] b) {
		final double[] v = new double[Math.max(a.length, b.length)];
		for(int i = 0; i < v.length; i++) v[i] = (a.length > i ? a[i] : 0) + (b.length > i ? b[i] : 0);
		return v;
	}

	/** Clamp a value
	 *
	 * @param a    The original value
	 * @param low  The lower bound
	 * @param high The upper bound
	 * @return The clamped value */
	public static float clamp(final float a, final float low, final float high) {
		return Math.max(low, Math.min(a, high));
	}

	/** Find the cross product of a vector and a float
	 *
	 * @param s The scalar float
	 * @param a The vector to fidn the cross of
	 * @return A newly created resultant vector */
	public static Vector2f cross(final float s, final Vector2f a) {
		return new Vector2f(-s * a.y, s * a.x);
	}

	/** Find the cross product of a vector and a float
	 *
	 * @param s The scalar float
	 * @param a The vector to fidn the cross of
	 * @return A newly created resultant vector */
	public static Vector2f cross(final Vector2f a, final float s) {
		return new Vector2f(s * a.y, -s * a.x);
	}
	
	/** Find the cross product of two vectors
	 *
	 * @param a The first vector
	 * @param b The second vector
	 * @return The cross product of the two vectors */
	public static float cross(final Vector2f a, final Vector2f b) {
		return a.x * b.y - a.y * b.x;
	}
	
	public static double det(final double[][] A) {
		final int n = A.length;
		if(n == 1) return A[0][0];
		double ans = 0;
		final double B[][] = new double[n - 1][n - 1];
		int l = 1;
		for(int i = 0; i < n; ++i) {
			int x = 0, y = 0;
			for(int j = 1; j < n; ++j) for(int k = 0; k < n; ++k) {
				if(i == k) continue;
				B[x][y] = A[j][k];
				++y;
				if(y == n - 1) {
					y = 0;
					++x;
				}
			}
			ans += l * A[0][i] * MathUtil.det(B);
			l *= -1;
		}
		return ans;
	}

	public static double[][] getE(final int n) {
		final double[][] E = new double[n][n];
		for(int i = 0; i < n; i++) E[i][i] = 1;
		return E;
	}
	
	/** Get the normal of a line x y (or edge). When standing on x facing y, the
	 * normal will point to the left.
	 *
	 * TODO: move this function somewhere else?
	 *
	 * @param x startingpoint of the line
	 * @param y endpoint of the line
	 * @return a (normalised) normal */
	public static Vector2f getNormal(final ROVector2f x, final ROVector2f y) {
		Vector2f normal = new Vector2f(y);
		normal.sub(x);
		normal = new Vector2f(normal.y, -normal.x);
		normal.normalise();
		return normal;
	}
	
	public static Vector[] getVectors(final double[][] f) {//no work
		final double[][] r = MathUtil.transposed(f);
		final Vector[] v = new Vector[r.length];
		for(int i = 0; i < v.length; i++) v[i] = new Vector(r[i]);
		return v;
	}
	
	public static double[][] inverse(final double[][] A0) throws MathException {
		if(A0[0].length != A0.length) throw new MathException();
		double temp;
		final int n = A0.length;
		final double[][] A = new double[n][n];
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) A[i][j] = A0[i][j];
		final double[][] E = MathUtil.getE(n);
		for(int k = 0; k < n; k++) {
			temp = A[k][k];
			for(int j = 0; j < n; j++) {
				A[k][j] /= temp;
				E[k][j] /= temp;
			}
			for(int i = k + 1; i < n; i++) {
				temp = A[i][k];
				for(int j = 0; j < n; j++) {
					A[i][j] -= A[k][j] * temp;
					E[i][j] -= E[k][j] * temp;
				}
			}
		}
		for(int k = n - 1; k > 0; k--) for(int i = k - 1; i >= 0; i--) {
			temp = A[i][k];
			for(int j = 0; j < n; j++) {
				A[i][j] -= A[k][j] * temp;
				E[i][j] -= E[k][j] * temp;
			}
		}
		return E;
	}
	
	public static double module(final double[] a) {
		float sum = 0;
		for(final double v : a) sum += v * v;
		return (float) Math.sqrt(sum);
	}
	
	/** Multiple two matricies
	 *
	 * @param A The first matrix
	 * @param B The second matrix
	 * @return A newly created matrix containing the result */
	public static Matrix2f mul(final Matrix2f A, final Matrix2f B) {
		return new Matrix2f(MathUtil.mul(A, B.col1), MathUtil.mul(A, B.col2));
	}

	/** Multiply a matrix by a vector
	 *
	 * @param A The matrix to be multiplied
	 * @param v The vector to multiple by
	 * @return A newly created vector containing the resultant vector */
	public static Vector2f mul(final Matrix2f A, final ROVector2f v) {
		return new Vector2f(A.col1.x * v.getX() + A.col2.x * v.getY(), A.col1.y * v.getX() + A.col2.y * v.getY());
	}
	
	public static double[] multiply(final double[] v, final double f) {
		for(int i = 0; i < v.length; i++) v[i] *= f;
		return v;
	}

	public static double[] multiply(final double[] v1, final double[] v2) {
		//TODO multiply
		return v1;
	}
	
	public static double[] multiply(double[] v, double[][] m) {
		m = MathUtil.transposed(m);
		if(v.length != m.length) return null;
		for(int i = 0; i < v.length; i++) MathUtil.multiply(m[i], v[i]);
		v = new double[v.length];
		for(final double[] f : m) v = MathUtil.addition(v, f);
		return v;
	}

	public static double[][] multiply(final double[][] a, final double f) {
		final double[][] m = a.clone();
		for(int i = 0; i < m.length; i++) for(int j = 0; j < m[i].length; j++) m[i][j] *= f;
		return m;
	}

	public static double[][] multiply(final double[][] m1, final double[][] m2) {
		if(m1[0].length != m2.length) return null;
		final double[][] mResult = new double[m1.length][m2[0].length];
		for(int i = 0; i < m1.length; i++) for(int j = 0; j < m2[0].length; j++)
			for(int k = 0; k < m1[0].length; k++) mResult[i][j] += m1[i][k] * m2[k][j];
		return mResult;
	}
	
	public static double[][] pow(final double[][] m0, int n) throws MathException {
		double[][] baze = null;
		double[][] m = m0.clone();
		if(n < 0) {
			baze = MathUtil.inverse(m);
			n *= -1;
		}else baze = m;
		for(int i = 1; i < n; i++) m = MathUtil.multiply(m, baze);
		return baze;
	}
	
	public static double scalarMultiply(final double[] a, final double[] b) {
		double res = 0;
		for(int i = 0; i < Math.min(a.length, b.length); i++) res += a[i] * b[i];
		return res;
	}
	
	/** Scale a vector by a given value
	 *
	 * @param a     The vector to be scaled
	 * @param scale The amount to scale the vector by
	 * @return A newly created vector - a scaled version of the new vector */
	public static Vector2f scale(final ROVector2f a, final float scale) {
		final Vector2f temp = new Vector2f(a);
		temp.scale(scale);
		return temp;
	}
	
	/** Check the sign of a value
	 *
	 * @param x The value to check
	 * @return -1.0f if negative, 1.0 if positive */
	public static float sign(final float x) {
		return x < 0.0f ? -1.0f : 1.0f;
	}
	
	/** Subtract one vector from another
	 *
	 * @param a The vector to be subtracted from
	 * @param b The vector to subtract
	 * @return A newly created containing the result */
	public static Vector2f sub(final ROVector2f a, final ROVector2f b) {
		final Vector2f temp = new Vector2f(a);
		temp.sub(b);
		return temp;
	}
	//	public static Vector2f intersect(Vector2f startA, Vector2f endA, Vector2f startB, Vector2f endB) {
	//		float d = (endB.y - startB.y) * (endA.x - startA.x) - (endB.x - startB.x) * (endA.y - startA.y);
	//
	//		if ( d == 0 ) // parallel lines
	//			return null;
	//
	//		float uA = (endB.x - startB.x) * (startA.y - startB.y) - (endB.y - startB.y) * (startA.x - startB.x);
	//		uA /= d;
	//		float uB = (endA.x - startA.x) * (startA.y - startB.y) - (endA.y - startA.y) * (startA.x - startB.x);
	//		uB /= d;
	//
	//		if ( uA < 0 || uA > 1 || uB < 0 || uB > 1 )
	//			return null; // intersection point isn't between the start and endpoints
	//
	//		return new Vector2f(
	//				startA.x + uA * (endA.x - startA.x),
	//				startA.y + uA * (endA.y - startA.y));
	//	}
	
	public static double[][] transposed(final double[][] m) {
		final double[][] r = new double[m[0].length][m.length];
		for(int i = 0; i < m.length; i++) for(int j = 0; j < m[i].length; j++) r[j][i] = m[i][j];
		return r;
	}
}
