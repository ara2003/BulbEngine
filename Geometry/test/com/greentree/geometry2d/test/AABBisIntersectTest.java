package com.greentree.geometry2d.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.greentree.engine.geom2d.Circle;
import com.greentree.engine.geom2d.Shape2D;

/**
 * @author Arseny Latyshev
 *
 */
@DisplayName("AABB 2D isIntersect Tests")
public class AABBisIntersectTest {
	
	@DisplayName("Circle as AABB isIntersect")
	@Test
	public void Circle() {
		
		Shape2D shape1 = new Circle(0, 0, 1);
		Shape2D shape2 = new Circle(0, 0, 1);
		Shape2D shape3 = new Circle(3, 0, 1);
		Shape2D shape4 = new Circle(0, 3, 1);
		Shape2D shape5 = new Circle(2, 2, 1.5f);
		
		assertTrue(shape1.getAABB().isIntersect(shape2.getAABB()));
		assertFalse(shape1.getAABB().isIntersect(shape3.getAABB()));
		assertFalse(shape1.getAABB().isIntersect(shape4.getAABB()));
		assertTrue(shape1.getAABB().isIntersect(shape5.getAABB()));
		assertFalse(shape2.getAABB().isIntersect(shape3.getAABB()));
		assertFalse(shape2.getAABB().isIntersect(shape4.getAABB()));
		assertTrue(shape2.getAABB().isIntersect(shape5.getAABB()));
		assertFalse(shape3.getAABB().isIntersect(shape4.getAABB()));
		assertTrue(shape3.getAABB().isIntersect(shape5.getAABB()));
		assertTrue(shape4.getAABB().isIntersect(shape5.getAABB()));
		
	}
	
}
