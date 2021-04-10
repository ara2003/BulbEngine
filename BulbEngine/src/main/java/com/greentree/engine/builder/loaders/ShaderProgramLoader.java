package com.greentree.engine.editor.loaders;

import java.io.IOException;
import java.util.Properties;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.ShaderLoaderI;
import com.greentree.bulbgl.shader.Shader;
import com.greentree.bulbgl.shader.ShaderProgram;
import com.greentree.common.loading.ResourceLoader;

/**
 * @author Arseny Latyshev
 *
 */
public class ShaderProgramLoader extends AbstractLoader<ShaderProgram> {

	public ShaderProgramLoader() {
		super(ShaderProgram.class);
	}
	
	@Override
	public ShaderProgram load(String value) throws Exception {
		if(!value.endsWith(".matirial")) throw new IllegalArgumentException();
		Properties properties = new Properties();
		try {
			properties.load(ResourceLoader.getResourceAsStream(value));
		}catch(IOException e) {
			e.printStackTrace();
		}
		String vertex = properties.getProperty("shader.vertex");
		String fragment = properties.getProperty("shader.fragment");
		String geometry = properties.getProperty("shader.geometry");
		String tessControl = properties.getProperty("shader.tess_control");
		String tessEvaluation = properties.getProperty("shader.tess_evaluation");
		
		if(vertex == null) throw new IllegalArgumentException("vertex shader required");
		if(fragment == null) throw new IllegalArgumentException("fragment shader required");
		
		ShaderLoaderI SL = BulbGL.getShaderLoader();
		
		ShaderProgram.Builder buider = SL.newShaderProgramBuilder();
		
		buider.addShader(SL.load(ResourceLoader.getResourceAsStream(vertex), Shader.Type.VERTEX));
		buider.addShader(SL.load(ResourceLoader.getResourceAsStream(fragment), Shader.Type.FRAGMENT));
		if(geometry != null)buider.addShader(SL.load(ResourceLoader.getResourceAsStream(geometry), Shader.Type.GEOMETRY));
		if(tessControl != null)buider.addShader(SL.load(ResourceLoader.getResourceAsStream(tessControl), Shader.Type.TESS_CONTROL));
		if(tessEvaluation != null)buider.addShader(SL.load(ResourceLoader.getResourceAsStream(tessEvaluation), Shader.Type.TESS_EVALUATION));
		
		return buider.build();
	}
	
}
