package com.greentree.graphics.shader;

import java.io.InputStream;

import org.lwjgl.opengl.GL30;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class GLShaderLoader {
	
	public static GLShaderProgram.Builder newShaderProgramBuilder() {
		return GLShaderProgram.builder();
	}
	
	public static Shader load(InputStream data, ShaderType type) {
		return GLSLShader.load(data, type);
	}
	
	public static VertexArray createVertexArray() {
		return new VertexArray(GL30.glGenVertexArrays()) {
			
			@Override
			public void bind() {
				GL30.glBindVertexArray(id);
			}

			@Override
			public void unbind() {
				GL30.glBindVertexArray(0);
			}
			
		};
	}
	
}
