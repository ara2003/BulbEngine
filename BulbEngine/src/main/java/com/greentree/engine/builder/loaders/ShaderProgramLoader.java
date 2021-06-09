package com.greentree.engine.builder.loaders;

import java.io.IOException;
import java.util.Properties;

import com.greentree.data.loaders.value.AbstractVlaueLoader;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.graphics.shader.GLShaderLoader;
import com.greentree.graphics.shader.GLShaderProgram;
import com.greentree.graphics.shader.ShaderProgram;
import com.greentree.graphics.shader.ShaderType;

/** @author Arseny Latyshev */
public class ShaderProgramLoader extends AbstractVlaueLoader<ShaderProgram> {

	public ShaderProgramLoader() {
		super(ShaderProgram.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShaderProgram parse(final String value) throws Exception {
		if(!value.endsWith(".matirial")) throw new IllegalArgumentException();
		final Properties properties = new Properties();
		try {
			properties.load(ResourceLoader.getResourceAsStream(value));
		}catch(final IOException e) {
			e.printStackTrace();
		}
		final String vertex         = properties.getProperty("shader.vertex");
		final String fragment       = properties.getProperty("shader.fragment");
		final String geometry       = properties.getProperty("shader.geometry");
		final String tessControl    = properties.getProperty("shader.tess_control");
		final String tessEvaluation = properties.getProperty("shader.tess_evaluation");

		if(vertex == null) throw new IllegalArgumentException("vertex shader required");
		if(fragment == null) throw new IllegalArgumentException("fragment shader required");

		final ShaderProgram.Builder buider = GLShaderProgram.builder();

		buider.addShader(GLShaderLoader.load(ResourceLoader.getResourceAsStream(vertex), ShaderType.VERTEX));
		buider.addShader(GLShaderLoader.load(ResourceLoader.getResourceAsStream(fragment), ShaderType.FRAGMENT));
		if(geometry != null) buider.addShader(GLShaderLoader.load(ResourceLoader.getResourceAsStream(geometry), ShaderType.GEOMETRY));
		if(tessControl != null) buider.addShader(GLShaderLoader.load(ResourceLoader.getResourceAsStream(tessControl), ShaderType.TESS_CONTROL));
		if(tessEvaluation != null) buider.addShader(GLShaderLoader.load(ResourceLoader.getResourceAsStream(tessEvaluation), ShaderType.TESS_EVALUATION));

		return buider.build();
	}

}
