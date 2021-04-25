package com.greentree.graphics.shader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;

import com.greentree.graphics.GLType;
import com.greentree.graphics.core.Decoder;

/** @author Arseny Latyshev */
public class GLVideoBuffer extends VideoBuffer {
	
	public GLVideoBuffer(final Type type, final Usage usage, GLType s) {
		super(GL15.glGenBuffers(), type, usage, s);
	}
	
	
	@Override
	public void bind() {
		GL15.glBindBuffer(Decoder.glEnum(this.type), this.getId());
	}
	
	@Override
	protected void bufferData(final Buffer data) {
		if(data instanceof ByteBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (ByteBuffer) data, Decoder.glEnum(this.usage));
		if(data instanceof ShortBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (ShortBuffer) data, Decoder.glEnum(this.usage));
		if(data instanceof IntBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (IntBuffer) data, Decoder.glEnum(this.usage));
		if(data instanceof FloatBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (FloatBuffer) data, Decoder.glEnum(this.usage));
		if(data instanceof DoubleBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (DoubleBuffer) data, Decoder.glEnum(this.usage));
		if(data instanceof LongBuffer) GL15.glBufferData(Decoder.glEnum(this.type), (LongBuffer) data, Decoder.glEnum(this.usage));
	}
	
	@Override
	public void delete() {
		GL15.glDeleteBuffers(this.getId());
	}
	
	@Override
	public void unbind() {
		GL15.glBindBuffer(Decoder.glEnum(this.type), 0);
	}

	public void setData(float[] data) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			setData(stack.floats(data));
		}
	}
	
}
