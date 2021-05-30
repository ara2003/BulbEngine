package com.greentree.geometry2d.test;

import com.greentree.engine.geom2d.Line2D;

public class Test {

	public static void main(String[] args) {
		Line2D line1 = new Line2D(-1, 0, 1, 0);
		Line2D line2 = new Line2D(0, -1, 0, 1);
		
		System.out.println(line1.getPenetrationDepth(line2));
	}

}
