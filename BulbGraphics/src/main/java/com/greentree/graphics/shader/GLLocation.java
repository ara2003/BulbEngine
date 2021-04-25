package com.greentree.graphics.shader;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;

/**
 * @author Arseny Latyshev
 *
 */
public class GLLocation {

	private final int id;
	
	public GLLocation(int id) {
		this.id = id;
	}

	public void glUniformMatrix4fv(boolean transpose, FloatBuffer matrix) {
		GL30.glUniformMatrix4fv(id, transpose, matrix);
	}
	
}
