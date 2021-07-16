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

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.greentree.graphics.GLType;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.shader.VideoBuffer.Type;
import com.greentree.graphics.shader.VideoBuffer.Usage;

/** OpenGL shader program object wraper
 * 
 * @author Viktor Gubin */
public class GLShaderProgram extends ShaderProgram {
	
	private final Deque<VideoBuffer> buffers;
	// program object OpenGL id
	private final int id;
	private final List<GLSLShader> shaders;
	
	private GLShaderProgram(final List<GLSLShader> shaders) {
		this.id      = GL20.glCreateProgram();
		this.shaders = shaders;
		for(final GLSLShader shader : shaders) GL20.glAttachShader(this.id, shader.getId());
		this.buffers = new LinkedList<>();
	}
	
	
	/** Returns build for loading shaders and constructing program object
	 * 
	 * @return new builder */
	public static Builder builder() {
		return new Builder();
	}
	
	
	
	/** Binds an shader attribute to a number
	 * 
	 * @param attrNo attribute number to bind
	 * @param name   attribute name as specified in vertex shader GLSL to
	 *               bind */
	public void bindAttribLocation(final int attrNo, final String name) {
		GL20.glBindAttribLocation(this.id, attrNo, name);
		Graphics.validateOpenGL();
	}
	
	/** Creates new OpenGL video buffer, i.e. allocates video memory and
	 * initialize it with the data
	 * 
	 * @param data  - a data to be stored in video buffer
	 * @param type  - video buffer type
	 * @param usage - vido buffer usage
	 * @return new video buffer */
	public VideoBuffer createVideoBuffer(final ByteBuffer data, final Type type, final Usage usage) {
		final VideoBuffer result = new GLVideoBuffer(type, usage, GLType.BYTE);
		result.setData(data);
		this.buffers.push(result);
		return result;
	}
	
	/** Creates new OpenGL video buffer, i.e. allocates video memory and
	 * initialize it with the data
	 * 
	 * @param data  - a data to be stored in video buffer
	 * @param type  - video buffer type
	 * @param usage - vido buffer usage
	 * @return new video buffer */
	public VideoBuffer createVideoBuffer(final DoubleBuffer data, final Type type, final Usage usage) {
		final VideoBuffer result = new GLVideoBuffer(type, usage, GLType.DOUBLE);
		result.setData(data);
		this.buffers.push(result);
		return result;
	}
	
	/** Creates new OpenGL video buffer, i.e. allocates video memory and
	 * initialize it with the data
	 * 
	 * @param data  - a data to be stored in video buffer
	 * @param type  - video buffer type
	 * @param usage - vido buffer usage
	 * @return new video buffer */
	@Override
	public VideoBuffer createVideoBuffer(final FloatBuffer data, final Type type, final Usage usage) {
		final VideoBuffer result = new GLVideoBuffer(type, usage, GLType.FLOAT);
		result.setData(data);
		this.buffers.push(result);
		return result;
	}
	
	/** Creates new OpenGL video buffer, i.e. allocates video memory and
	 * initialize it with the data
	 * 
	 * @param data  - a data to be stored in video buffer
	 * @param type  - video buffer type
	 * @param usage - vido buffer usage
	 * @return new video buffer */
	public VideoBuffer createVideoBuffer(final IntBuffer data, final Type type, final Usage usage) {
		final VideoBuffer result = new GLVideoBuffer(type, usage, GLType.UNSIGNED_INT);
		result.setData(data);
		this.buffers.push(result);
		return result;
	}
	
	/** Creates new OpenGL video buffer, i.e. allocates video memory and
	 * initialize it with the data
	 * 
	 * @param data  - a data to be stored in video buffer
	 * @param type  - video buffer type
	 * @param usage - vido buffer usage
	 * @return new video buffer */
	@Override
	public VideoBuffer createVideoBuffer(final ShortBuffer data, final Type type, final Usage usage) {
		final VideoBuffer result = new GLVideoBuffer(type, usage, GLType.UNSIGNED_SHORT);
		result.setData(data);
		this.buffers.push(result);
		return result;
	}
	
	/** Deletes program object and free all associated resources. Should be
	 * called manually to prevent video memory leaks */
	public void delete() {
		GL20.glDeleteProgram(this.id);
		while(!this.buffers.isEmpty()) this.buffers.pop().delete();
	}
	
	/** Locates a uniform id in this program
	 * 
	 * @param name - uniform name as specified in GLSL
	 * @return uniform location or -1 if uniform is not found */
	@Override
	public GLLocation getUniformLocation(final String name) {
		return new GLLocation(GL20.glGetUniformLocation(this.id, name));
	}
	
	/** Links and validates this program object */
	@Override
	public void link() {
		GL20.glLinkProgram(this.id);
		this.validateParam(GL20.GL_LINK_STATUS, "Shader program link error:\n");
		GL20.glValidateProgram(this.id);
		this.validateParam(GL20.GL_VALIDATE_STATUS, "Shader program validate error:\n");
		// release shader objects, since no longer needed and just taking resources
		for(final GLSLShader shader : this.shaders) {
			GL20.glDetachShader(this.id, shader.getId());
			shader.delete();
		}
	}
	
	/** Passes vertex attribute array into program object
	 * 
	 * @param attrNo     shader attribute number see
	 *                   {@link GLShaderProgram#bindAttribLocation}
	 * @param vbo        vertex buffer object to take data from
	 * @param normalized whether attribute data needs to be normalized before
	 *                   passing to program
	 * @param vertexSize size of vertex attribute components i.e. 2,3 or 4
	 * @param stride     - i.e. of whole vertex attribute size. For
	 *                   <code>{px,py,pz}{nz,ny,nz}</code> format would be 6
	 *                   since 3 floats for position and 3 floats for normal
	 *                   vector
	 * @param offset     - offset from vertex attribute component begin,
	 *                   <code>{px,py,pz}{nz,ny,nz}</code> format should be 0
	 *                   when binding position and 3 when binding normal
	 *                   vector */
	public void passVertexAttribArray(final int attrNo, final VideoBuffer vbo, final boolean normalized, final int vertexSize, final int stride,
		final int offset) {
		if(VideoBuffer.Type.ARRAY_BUFFER != vbo.getType()) throw new IllegalArgumentException("Array buffer expected");
		final int  dtpSize         = vbo.getDataType().sizeOf();
		final int  byteStride      = stride * dtpSize;
		final long componentOffset = offset * dtpSize;
		vbo.bind();
		GL20.glVertexAttribPointer(attrNo, vertexSize, vbo.getDataType().glEnum(), normalized, byteStride, componentOffset);
		Graphics.validateOpenGL();
		GL20.glEnableVertexAttribArray(attrNo);
		Graphics.validateOpenGL();
		vbo.unbind();
	}
	
	/** Passes whole vertex attributes into program with layout. Can be used
	 * only when all vertex attributes are line stored in the single VBO
	 * 
	 * @param vbo        - a vertex buffer object with the data
	 * @param normalized whether attribute data needs to be normalized before
	 *                   passing to program
	 * @param layout     combination of vertex attribute name an vertex and
	 *                   size */
	@Override
	public void passVertexAttribArray(final VideoBuffer vbo, final boolean normalized, final Attribute... layout) {
		int vertexSize = 0;
		for(int i = 0; i < layout.length; i++) {
			this.bindAttribLocation(i, layout[i].getName());
			vertexSize += layout[i].getStride();
		}
		int offset = 0;
		for(int i = 0; i < layout.length; i++) {
			this.passVertexAttribArray(i, vbo, normalized, layout[i].getStride(), vertexSize, offset);
			offset += layout[i].getStride();
		}
	}
	
	/** Install this program as part of current rendering state */
	@Override
	public void start() {
		GL20.glUseProgram(this.id);
	}
	
	/** Sets current rendering state to default */
	@Override
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	
	@Override
	public String toString() {
		return "Program(" + this.id + ")" + this.shaders;
	}
	
	
	private void validateParam(final int pname, final String errFormat) {
		final int[] errc = new int[2];
		GL20.glGetProgramiv(this.id, pname, errc);
		if(GL11.GL_FALSE == errc[0]) {
			GL20.glGetProgramiv(this.id, GL20.GL_INFO_LOG_LENGTH, errc);
			throw new IllegalStateException(String.format("%s %s", errFormat, GL20.glGetProgramInfoLog(this.id, errc[1])));
		}
	}
	
	
	public static class Builder extends ShaderProgram.Builder {
		
		private final List<GLSLShader> shaders;
		
		Builder() {
			this.shaders = new ArrayList<>(6);
		}
		
		@Override
		public Builder addShader(final Shader shader) {
			this.shaders.add((GLSLShader) shader);
			return this;
		}
		
		@Override
		public GLShaderProgram build() {
			if(!this.shaders.stream().anyMatch(s->ShaderType.VERTEX == s.getType())) throw new IllegalStateException("Vertex shader is manadatory");
			if(!this.shaders.stream().anyMatch(s->ShaderType.FRAGMENT == s.getType())) throw new IllegalStateException("Fragment shader is manadatory");
			if(this.shaders.stream().anyMatch(s->ShaderType.TESS_CONTROL == s.getType())
				&& !this.shaders.stream().anyMatch(s->ShaderType.TESS_EVALUATION == s.getType()))
				throw new IllegalStateException("Tesselation control shader exist, but no tesselation evaluation shader load");
			else if(!this.shaders.stream().anyMatch(s->ShaderType.TESS_CONTROL == s.getType())
				&& this.shaders.stream().anyMatch(s->ShaderType.TESS_EVALUATION == s.getType()))
				throw new IllegalStateException("Tesselation evaluetion shader exist, but no tesselation control shader load");
			return new GLShaderProgram(this.shaders);
		}
		
	}
	
}
