package com.greentree.graphics.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

/** @author Arseny Latyshev Supported OpenGL 4.3+ shader types */
public enum ShaderType{
	
	COMPUTE(GL43.GL_COMPUTE_SHADER),
	FRAGMENT(GL20.GL_FRAGMENT_SHADER),
	GEOMETRY(GL32.GL_GEOMETRY_SHADER),
	TESS_CONTROL(GL40.GL_TESS_CONTROL_SHADER),
	TESS_EVALUATION(GL40.GL_TESS_EVALUATION_SHADER),
	VERTEX(GL20.GL_VERTEX_SHADER);

	private final int glEnum;

	
	public int getGlEnum() {
		return glEnum;
	}

	private ShaderType(int glEnum) {
		this.glEnum = glEnum;
	}
	
	
	
}
