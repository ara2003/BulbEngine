package com.greentree.bulbgl.shader;

/** @author Arseny Latyshev */
public abstract class Shader {
	
	
	/** Supported OpenGL 4.3+ shader types */
	public enum Type{
		
		COMPUTE,
		FRAGMENT,
		GEOMETRY,
		TESS_CONTROL,
		TESS_EVALUATION,
		VERTEX
	
	}
	
	
}
