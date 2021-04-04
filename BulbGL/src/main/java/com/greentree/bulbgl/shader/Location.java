package com.greentree.bulbgl.shader;

import java.nio.FloatBuffer;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class Location {

	public abstract void glUniformMatrix4fv(boolean b, FloatBuffer mvp);
	
}
