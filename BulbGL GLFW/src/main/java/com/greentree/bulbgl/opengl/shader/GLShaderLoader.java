package com.greentree.bulbgl.opengl.shader;

import java.io.InputStream;

import org.lwjgl.opengl.GL30;

import com.greentree.bulbgl.ShaderLoaderI;
import com.greentree.bulbgl.shader.Shader;
import com.greentree.bulbgl.shader.Shader.Type;
import com.greentree.bulbgl.shader.ShaderProgram.Builder;
import com.greentree.bulbgl.shader.VertexArray;


/**
 * @author Arseny Latyshev
 *
 */
public class GLShaderLoader implements ShaderLoaderI {
	
	@Override
	public Builder newShaderProgramBuilder() {
		return GLShaderProgram.builder();
	}
	
	@Override
	public Shader load(InputStream data, Type type) {
		return GLSLShader.load(data, type);
	}
	
	@Override
	public VertexArray createVertexArray() {
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
