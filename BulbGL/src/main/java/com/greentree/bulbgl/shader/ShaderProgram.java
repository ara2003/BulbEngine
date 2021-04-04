package com.greentree.bulbgl.shader;

import com.greentree.bulbgl.shader.VideoBuffer.Type;
import com.greentree.bulbgl.shader.VideoBuffer.Usage;

/** @author Arseny Latyshev */
public abstract class ShaderProgram {
	
	
	public abstract VideoBuffer createVideoBuffer(float[] vertexAndTexCoordAndNormal, Type arrayBuffer, Usage staticDraw);
	
	
	public abstract VideoBuffer createVideoBuffer(short[] indecies, Type elementArrayBuffer, Usage staticDraw);
	
	public abstract Location getUniformLocation(String ame);
	public abstract void link();
	public abstract void passVertexAttribArray(VideoBuffer vbo, boolean b, Attribute of, Attribute of2, Attribute of3);
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
	
}
