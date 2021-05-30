package com.greentree.geometry2d.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.greentree.engine.geom2d.Circle;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.Shape2D;

/**
 * @author Arseny Latyshev
 *
 */
@DisplayName("Inside 2D isInside Tests")
public class Inside {
	
	@Test
	public void Circle() {
		Shape2D shape1 = new Circle(0, 0, 1);
		Shape2D shape2 = new Circle(0, 0, 1);
		Shape2D shape3 = new Circle(3, 0, 1);
		Shape2D shape4 = new Circle(0, 3, 1);
		Shape2D shape5 = new Circle(2, 2, 1.5f);
		
		Point2D p = new Point2D(0, 0);
		
		System.out.println(shape1.isInside(p));
		
	}
	
}
