package com.greentree.graphics.shader;

import org.lwjgl.opengl.GL30;


/**
 * @author Arseny Latyshev
 *
 */
public class GLVertexArray extends VertexArray {
	
	public GLVertexArray() {
		super(GL30.glGenVertexArrays());
	}

	@Override
	public void bind() {
		GL30.glBindVertexArray(id);
	}
	
	@Override
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	
}
