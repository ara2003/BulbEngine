/*
Copyright 2020 Viktor Gubin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.greentree.graphics.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;


/** OpenGL shader object helper
 * 
 * @author Viktor Gubin */
public final class GLSLShader extends Shader {
	
	private final int id;
	
	private final ShaderType type;
	
	private GLSLShader(final int id, final ShaderType type) {
		this.id   = id;
		this.type = type;
	}
	
	private static String getLogInfo(final int id) {
		return GL20.glGetShaderInfoLog(id, 1024);
	}
	
	/** Loads GLSL shader source code from a stream
	 * @param source a stream to load shader from, will be closed
	 * @param type   shader type to load
	 * @return new shader object
	 * @throws IllegalStateException when shader can not be read, or compiled */
	public static GLSLShader load(final InputStream source, final ShaderType type) {
		return GLSLShader.load(GLSLShader.loadSource(source), type);
	}
	
	private static GLSLShader load(final String source, final ShaderType type) {
		final int id = GL20.glCreateShader(type.getGlEnum());
		GL20.glShaderSource(id, source);
		GL20.glCompileShader(id);
		final int[] errc = {GL11.GL_TRUE};
		GL20.glGetShaderiv(id, GL20.GL_COMPILE_STATUS, errc);
		if(GL11.GL_FALSE == errc[0]) throw new IllegalStateException(String.format("Error creating %s shader: %s", type.name(), GLSLShader.getLogInfo(id)));
		return new GLSLShader(id, type);
	}
	
	private static String loadSource(final InputStream source) {
		try(Reader reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
			final StringBuilder result = new StringBuilder();
			final char[]        buff   = new char[512]; // 1k
			for(int read = reader.read(buff); read > 0; read = reader.read(buff)) result.append(buff, 0, read);
			return result.toString();
		}catch(final IOException exc) {
			throw new IllegalStateException("Can not load shader source", exc);
		}
	}
	
	void delete() {
		GL20.glDeleteShader(this.id);
	}
	
	int getId() {
		return this.id;
	}
	
	/** Returns this shader object type
	 * @return */
	public ShaderType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return "Shader [id=" + this.id + ", type=" + this.type + "]";
	}
	
}
