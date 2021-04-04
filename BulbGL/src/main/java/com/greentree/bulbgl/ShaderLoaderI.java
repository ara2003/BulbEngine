package com.greentree.bulbgl;

import java.io.InputStream;

import com.greentree.bulbgl.shader.Shader;
import com.greentree.bulbgl.shader.ShaderProgram;
import com.greentree.bulbgl.shader.VertexArray;

/**
 * @author Arseny Latyshev
 *
 */
public interface ShaderLoaderI {
	
	ShaderProgram.Builder newShaderProgramBuilder();
	Shader load(InputStream data, Shader.Type type);
	VertexArray createVertexArray();
	
}
