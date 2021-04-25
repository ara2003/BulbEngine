package com.greentree.graphics.core;

import org.lwjgl.opengl.ARBQueryBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.GLType;
import com.greentree.graphics.PixelFormat;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.shader.VideoBuffer;
import com.greentree.graphics.texture.Filtering;

/** @author Arseny Latyshev */
public final class Decoder {
	

	public final static int glEnum(final Filtering f) {
		return switch(f) {
			case LINEAR -> GL11.GL_LINEAR;
			case NEAREST -> GL11.GL_NEAREST;
		};
	}
	
	public static int glEnum(final PixelFormat pixelFormat) {
		return switch(pixelFormat) {
			case RGB -> GL11.GL_RGB;
			case RGBA -> GL11.GL_RGBA;
		};
	}
	
	public final static int glEnum(final Wrapping wrap) {
		return switch(wrap) {
			case REPEAT -> GL11.GL_REPEAT;
			case MIRRORED_REPEAT -> GL14.GL_MIRRORED_REPEAT;
			case CLAMP_TO_BORDER -> GL13.GL_CLAMP_TO_BORDER;
			case CLAMP_TO_EDGE -> GL12.GL_CLAMP_TO_EDGE;
		};
	}
	
	public static int glEnum(final VideoBuffer.Type type) {
		return switch(type) {
			case ARRAY_BUFFER -> GL15.GL_ARRAY_BUFFER;
			case ATOMIC_COUNTER -> GL42.GL_ATOMIC_COUNTER_BUFFER;
			case COPY_READ_BUFFER -> GL31.GL_COPY_READ_BUFFER;
			case COPY_WRITE_BUFFER -> GL31.GL_COPY_WRITE_BUFFER;
			case DISPATCH_INDIRECT_BUFFER -> GL43.GL_DISPATCH_INDIRECT_BUFFER;
			case DRAW_INDIRECT_BUFFER -> GL40.GL_DRAW_INDIRECT_BUFFER;
			case ELEMENT_ARRAY_BUFFER -> GL15.GL_ELEMENT_ARRAY_BUFFER;
			case QUERY_BUFFER -> ARBQueryBufferObject.GL_QUERY_BUFFER;
			case PIXEL_PACK_BUFFER -> GL21.GL_PIXEL_PACK_BUFFER;
			case PIXEL_UNPACK_BUFFER -> GL21.GL_PIXEL_UNPACK_BUFFER;
			case SHADER_STORAGE_BUFFER -> GL43.GL_SHADER_STORAGE_BUFFER;
			case TEXTURE_BUFFER -> GL31.GL_TEXTURE_BUFFER;
			case UNIFORM_BUFFER -> GL31.GL_UNIFORM_BUFFER;
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}
	
	public static int glEnum(final VideoBuffer.Usage usage) {
		return switch(usage) {
			case DYNAMIC_COPY -> GL15.GL_DYNAMIC_COPY;
			case DYNAMIC_DRAW -> GL15.GL_DYNAMIC_DRAW;
			case DYNAMIC_READ -> GL15.GL_DYNAMIC_READ;
			case STATIC_COPY -> GL15.GL_STATIC_COPY;
			case STATIC_DRAW -> GL15.GL_STATIC_DRAW;
			case STATIC_READ -> GL15.GL_DYNAMIC_READ;
			case STREAM_COPY -> GL15.GL_STREAM_COPY;
			case STREAM_DRAW -> GL15.GL_STREAM_DRAW;
			case STREAM_READ -> GL15.GL_STREAM_READ;
			default -> throw new IllegalArgumentException("Unexpected value: " + usage);
		};
	}

	public static int glEnum(GLPrimitive p) {
		return switch(p) {
			case TRIANGLES -> GL15.GL_TRIANGLES;
			case QUADS -> GL15.GL_QUADS;
			case POINTS -> GL15.GL_POINTS;
			case LINE_LOOP -> GL15.GL_LINE_LOOP;
			case LINE_STRIP -> GL15.GL_LINE_STRIP;
			case LINES -> GL15.GL_LINES;
			case POLYGON -> GL15.GL_POLYGON;
			case QUAD_STRIP -> GL15.GL_QUAD_STRIP;
			case TRIANGLE_FAN -> GL15.GL_TRIANGLE_FAN;
			case TRIANGLE_STRIP -> GL15.GL_TRIANGLE_STRIP;
			default -> throw new IllegalArgumentException("Unexpected value: " + p);
		};
	}

	@Deprecated
	public static int glEnum(GLType type) {
		return type.glEnum();
	}
	
}
