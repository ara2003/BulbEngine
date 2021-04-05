package com.greentree.bulbgl.opengl.shader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

import com.greentree.bulbgl.DataType;
import com.greentree.bulbgl.opengl.Decoder;
import com.greentree.bulbgl.shader.VideoBuffer;

/** @author Arseny Latyshev */
public class GLVideoBuffer extends VideoBuffer {
	
	public GLVideoBuffer(final Type type, final Usage usage, DataType s) {
		super(GL15.glGenBuffers(), type, usage, s);
	}
	
	
	@Override
	public void bind() {
		GL15.glBindBuffer(Decoder.glType(this.type), this.id);
	}
	
	@Override
	protected void bufferData(final Buffer data) {
		if(data instanceof ByteBuffer) GL15.glBufferData(Decoder.glType(this.type), (ByteBuffer) data, Decoder.glType(this.usage));
		if(data instanceof ShortBuffer) GL15.glBufferData(Decoder.glType(this.type), (ShortBuffer) data, Decoder.glType(this.usage));
		if(data instanceof IntBuffer) GL15.glBufferData(Decoder.glType(this.type), (IntBuffer) data, Decoder.glType(this.usage));
		if(data instanceof FloatBuffer) GL15.glBufferData(Decoder.glType(this.type), (FloatBuffer) data, Decoder.glType(this.usage));
		if(data instanceof DoubleBuffer) GL15.glBufferData(Decoder.glType(this.type), (DoubleBuffer) data, Decoder.glType(this.usage));
		if(data instanceof LongBuffer) GL15.glBufferData(Decoder.glType(this.type), (LongBuffer) data, Decoder.glType(this.usage));
	}
	
	@Override
	public void delete() {
		GL15.glDeleteBuffers(this.id);
	}
	
	@Override
	public void unbind() {
		GL15.glBindBuffer(Decoder.glType(this.type), 0);
	}
	
}
