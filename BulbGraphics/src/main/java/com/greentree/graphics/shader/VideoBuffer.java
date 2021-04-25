package com.greentree.graphics.shader;

import java.nio.Buffer;

import com.greentree.graphics.GLType;

/**
 * 
 * @author Viktor Gubin
 *
 */
public abstract class VideoBuffer {

	private final int id;
	protected int size;
	protected final Type type;
	protected final Usage usage;	
	protected final GLType dataType;


	public final void setData(final Buffer data) {
		this.size = data.remaining();
		bind();
		try {
			bufferData(data);
		}finally {
			unbind();
		}
	}
	
	protected abstract void bufferData(Buffer data);

	public VideoBuffer(int id, Type type, Usage usage, GLType dataType) {
		this.id = id;
		this.size = 0;
		this.type = type;
		this.usage = usage;
		this.dataType = dataType;
	}

	protected final int getId() {
		return id;
	}
	
	public int getSize() {
		return size;
	}

	public Type getType() {
		return type;
	}

	public Usage getUsage() {
		return usage;
	}

	public GLType getDataType() {
		return dataType;
	}

	public abstract void bind();

	public abstract void unbind();

	public abstract void  delete();
	
	public static enum Type {
		ARRAY_BUFFER,
		ATOMIC_COUNTER,
		COPY_READ_BUFFER,
		COPY_WRITE_BUFFER,
		DISPATCH_INDIRECT_BUFFER,
		DRAW_INDIRECT_BUFFER,
		ELEMENT_ARRAY_BUFFER,
		QUERY_BUFFER,
		PIXEL_PACK_BUFFER,
		PIXEL_UNPACK_BUFFER,
		SHADER_STORAGE_BUFFER,
		TEXTURE_BUFFER,
		TRANSFORM_FEEDBACK_BUFFER,
		UNIFORM_BUFFER;
	}

	public enum Usage {
		DYNAMIC_COPY,
		DYNAMIC_DRAW, 
		DYNAMIC_READ,
		STATIC_COPY, 
		STATIC_DRAW, 
		STATIC_READ, 
		STREAM_COPY,
		STREAM_DRAW, 
		STREAM_READ;
	}

}
