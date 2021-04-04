package com.greentree.bulbgl.shader;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class VertexArray {
	
	protected final int id;
	
	public VertexArray(int vao) {
		id = vao;
	}
	
	public abstract void bind();
	public abstract void unbind();
	
}
