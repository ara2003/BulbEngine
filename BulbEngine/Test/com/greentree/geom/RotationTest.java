package com.greentree.geom;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.greentree.util.Pair;

public class RotationTest {
	
	List<Pair<Pair<Point, Point>, Pair<Point, Double>>> points = Arrays.asList(
			new Pair<>(new Pair<>(new Point(0, 0), new Point(0, 0)), new Pair<>(new Point(0, 0), PI / 2)),
			new Pair<>(new Pair<>(new Point(-1, 0), new Point(1, -2)), new Pair<>(new Point(1, 0), PI / 2)),
			new Pair<>(new Pair<>(new Point(-1, 0), new Point(-2, -1)), new Pair<>(new Point(-2, 0), -PI / 2)),
			new Pair<>(new Pair<>(new Point(0, 0), new Point(0, 2)), new Pair<>(new Point(0, 1), PI)));
	
	@Test
	public void line() {
		final Line line1 = new Line(1, 0, -1, 0), line2 = new Line(1, 0, -1, 0);
		line1.rotate(new Point(0, 0), PI / 2);
		assertTrue(line1.getVector().dot(line2.getVector()) < 1E-9);
		line2.rotate(new Point(0, 0), PI / 2);
		assertEquals(line1, line2);
	}
	
	@Test
	public void point() {
		for(final Pair<Pair<Point, Point>, Pair<Point, Double>> pair : points) {
			final Point test = pair.first.first, res = pair.first.second, center = pair.second.first;
			final double ang = pair.second.second;
			test.rotate(center, ang);
			assertEquals(test, res);
			test.rotate(center, 2 * PI);
			assertEquals(test, res);
			test.rotate(center, -ang);
			test.rotate(center, 2 * PI + ang);
			assertEquals(test, res);
		}
	}
}
