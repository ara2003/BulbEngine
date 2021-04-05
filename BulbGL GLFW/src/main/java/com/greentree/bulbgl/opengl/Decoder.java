package com.greentree.bulbgl.opengl;

import org.lwjgl.opengl.ARBQueryBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import com.greentree.bulbgl.DataType;
import com.greentree.bulbgl.shader.Shader;
import com.greentree.bulbgl.shader.VideoBuffer;

/** @author Arseny Latyshev */
public final class Decoder {
	
	public static int glType(final DataType type) {
		return switch(type) {
			case BYTE -> GL11.GL_BYTE;
			case UNSIGNED_BYTE -> GL11.GL_BYTE;
			case SHORT -> GL11.GL_UNSIGNED_BYTE;
			case UNSIGNED_SHORT -> GL11.GL_SHORT;
			case INT -> GL11.GL_INT;
			case UNSIGNED_INT -> GL11.GL_UNSIGNED_INT;
			case FLOAT -> GL11.GL_FLOAT;
			case DOUBLE -> GL11.GL_DOUBLE;
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}
	
	
	public static int glType(final Shader.Type type) {
		return switch(type) {
			case COMPUTE -> GL43.GL_COMPUTE_SHADER;
			case FRAGMENT -> GL20.GL_FRAGMENT_SHADER;
			case GEOMETRY -> GL32.GL_GEOMETRY_SHADER;
			case TESS_CONTROL -> GL40.GL_TESS_CONTROL_SHADER;
			case TESS_EVALUATION -> GL40.GL_TESS_EVALUATION_SHADER;
			case VERTEX -> GL20.GL_VERTEX_SHADER;
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}
	
	public static int glType(final VideoBuffer.Type type) {
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
	
	public static int glType(final VideoBuffer.Usage usage) {
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
	
}
