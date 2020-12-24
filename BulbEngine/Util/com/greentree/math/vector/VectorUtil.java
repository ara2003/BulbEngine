package com.greentree.math.vector;

import com.greentree.math.MathException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VectorUtil {
	
	
	
	/** Prevent construction */
	private VectorUtil() {
	}
	
	
	
	public static double[] addition(final double[] a, final double[] b) {
		final double[] v = new double[Math.max(a.length, b.length)];
		for(int i = 0; i < v.length; i++) v[i] = (a.length > i ? a[i] : 0) + (b.length > i ? b[i] : 0);
		return v;
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
				if(y == (n - 1)) {
					y = 0;
					++x;
				}
			}
			ans += l * A[0][i] * VectorUtil.det(B);
			l *= -1;
		}
		return ans;
	}
	
	
	
	public static double[][] getE(final int n) {
		final double[][] E = new double[n][n];
		for(int i = 0; i < n; i++) E[i][i] = 1;
		return E;
	}
	
	public static Vector[] getVectors(final double[][] f) {//no work
		final double[][] r = VectorUtil.transposed(f);
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
		final double[][] E = VectorUtil.getE(n);
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
	
	public static double[] multiply(final double[] v, final double f) {
		for(int i = 0; i < v.length; i++) v[i] *= f;
		return v;
	}
	
	public static double[] multiply(final double[] v1, final double[] v2) {
		//TODO multiply
		return v1;
	}
	
	public static double[] multiply(double[] v, double[][] m) {
		m = VectorUtil.transposed(m);
		if(v.length != m.length) return null;
		for(int i = 0; i < v.length; i++) VectorUtil.multiply(m[i], v[i]);
		v = new double[v.length];
		for(final double[] f : m) v = VectorUtil.addition(v, f);
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
			baze = VectorUtil.inverse(m);
			n *= -1;
		}else baze = m;
		for(int i = 1; i < n; i++) m = VectorUtil.multiply(m, baze);
		return baze;
	}
	
	public static double scalarMultiply(final double[] a, final double[] b) {
		double res = 0;
		for(int i = 0; i < Math.min(a.length, b.length); i++) res += a[i] * b[i];
		return res;
	}
	
	public static double[][] transposed(final double[][] m) {
		final double[][] r = new double[m[0].length][m.length];
		for(int i = 0; i < m.length; i++) for(int j = 0; j < m[i].length; j++) r[j][i] = m[i][j];
		return r;
	}
}
