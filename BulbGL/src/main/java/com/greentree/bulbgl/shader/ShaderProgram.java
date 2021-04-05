package com.greentree.bulbgl.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.greentree.bulbgl.shader.VideoBuffer.Type;
import com.greentree.bulbgl.shader.VideoBuffer.Usage;

/** @author Arseny Latyshev */
public abstract class ShaderProgram {
	
	public abstract Location getUniformLocation(String ame);
	public abstract void link();
	public abstract void passVertexAttribArray(VideoBuffer vbo, boolean b, Attribute...of);
	public abstract void start();
	
	public abstract void stop();
	
	public static final class Attribute {
		
		private final String name;
		private final int stride;
		
		private Attribute(final String name, final int stide) {
			this.name   = name;
			this.stride = stide;
		}
		
		public static Attribute of(final String name, final int stride) {
			return new Attribute(name, stride);
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getStride() {
			return this.stride;
		}
	}
	
	public static abstract class Builder {
		
		public abstract Builder addShader(Shader shader);
		public abstract ShaderProgram build();
		
	}

	public abstract VideoBuffer createVideoBuffer(IntBuffer buffer, Type type, Usage usage);
	public abstract VideoBuffer createVideoBuffer(FloatBuffer buffer, Type type, Usage usage);
	public abstract VideoBuffer createVideoBuffer(ShortBuffer buffer, Type type, Usage usage);
	
}
