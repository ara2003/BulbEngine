package com.greentree.bulbgl.opengl.shader;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;

import com.greentree.bulbgl.shader.Location;

/**
 * @author Arseny Latyshev
 *
 */
public class GLLocation extends Location {

	private final int id;
	
	public GLLocation(int id) {
		this.id = id;
	}

	@Override
	public void glUniformMatrix4fv(boolean transpose, FloatBuffer matrix) {
		GL30.glUniformMatrix4fv(id, transpose, matrix);
	}
	
}
