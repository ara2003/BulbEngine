package com.greentree.bulbgl.glfw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.loading.ResourceLoader;

/** @author Arseny Latyshev */
@Deprecated
public class GLTReanglse {
	
	private static int shaderProgram;
	private static int VAO;
	
	static float vertices[] = {
        0.5f,  0.5f, 0.0f,  // Top Right
        0.5f, -0.5f, 0.0f,  // Bottom Right
       -0.5f, -0.5f, 0.0f,  // Bottom Left
       -0.5f,  0.5f, 0.0f   // Top Left 
   };
   static short indices[] = {  // Note that we start from 0!
       0, 1, 3,  // First Triangle
       1, 2, 3   // Second Triangle
   };
	
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
	
	public static void start(final float[] vertices) {
		{
			int success;
			
			final int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			GL20.glShaderSource(vertexShader, GLTReanglse.loadSource(ResourceLoader.getResourceAsStream("Shader/cube.vtx.glsl")));
			GL20.glCompileShader(vertexShader);
			success = GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS);
			if(success == GL11.GL_FALSE) {
				final String infoLog = GL20.glGetShaderInfoLog(vertexShader);
				throw new RuntimeException("ERROR::SHADER::PROGRAM::LINKING_FAILED " + success + " " + infoLog);
			}
			
			final int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			GL20.glShaderSource(fragmentShader, GLTReanglse.loadSource(ResourceLoader.getResourceAsStream("Shader/cube.frag.glsl")));
			GL20.glCompileShader(fragmentShader);
			success = GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS);
			if(success == GL11.GL_FALSE) {
				final String infoLog = GL20.glGetShaderInfoLog(fragmentShader);
				throw new RuntimeException("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
			}
			
			GLTReanglse.shaderProgram = GL20.glCreateProgram();
			GL20.glAttachShader(GLTReanglse.shaderProgram, vertexShader);
			GL20.glAttachShader(GLTReanglse.shaderProgram, fragmentShader);
			GL20.glLinkProgram(GLTReanglse.shaderProgram);
			
			// Check for linking errors
			success = GL20.glGetProgrami(GLTReanglse.shaderProgram, GL20.GL_LINK_STATUS);
			if(success == GL11.GL_FALSE) {
				final String infoLog = GL20.glGetProgramInfoLog(GLTReanglse.shaderProgram);
				throw new RuntimeException("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
			}
			
			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);
		}
		GLTReanglse.VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(GLTReanglse.VAO);
		//VBO
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			final FloatBuffer v = stack.callocFloat(vertices.length);
			v.put(vertices);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, v, GL15.GL_STATIC_DRAW);
			
		}
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 12, 0L);
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//EBO
		int ebo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);

		try(MemoryStack stack = MemoryStack.stackPush()) {
			final ShortBuffer v = stack.callocShort(indices.length);
			v.put(indices);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, v, GL20.GL_STATIC_DRAW);
			
		}
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		    
		GL30.glBindVertexArray(0);
	}
	
	public static void update() {
		GLTReanglse.validate();
		GL20.glUseProgram(GLTReanglse.shaderProgram);
		GLTReanglse.validate();
		GL30.glBindVertexArray(GLTReanglse.VAO);
		GLTReanglse.validate();
		
//		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

		System.out.println("ren");
		GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_SHORT, 0L);
		System.out.println("ren");
		
		GLTReanglse.validate();
		GL30.glBindVertexArray(0);
		GLTReanglse.validate();
		GL20.glUseProgram(0);
		GLTReanglse.validate();
	}
	
	private static void validate() {
		final int err = GL11.glGetError();
		if(err != GL11.GL_NO_ERROR) throw new RuntimeException(err + "");
		
	}
	
}
