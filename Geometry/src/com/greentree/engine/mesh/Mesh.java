package com.greentree.engine.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.greentree.common.logger.Log;
import com.greentree.engine.mesh.Mesh.Builder.VertexIndex;

public class Mesh {
	
	private final List<Vector3f> vertices;
	private final List<Vector2f> textureCoords;
	private final List<Vector3f> normals;
	private final List<Face> faces;
	
	public Mesh(final List<Vector3f> vertices, final List<Vector2f> textureCoords, final List<Vector3f> normals, final List<Face> faces) {
		this.vertices      = vertices;
		this.textureCoords = textureCoords;
		this.normals       = normals;
		this.faces         = faces;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	public IndeciesArray get(final Type... types) {
		final List<VertexIndex> list = new ArrayList<>();
		for(final Face f : this.faces) {
			list.add(f.vertexIndices[0]);
			list.add(f.vertexIndices[1]);
			list.add(f.vertexIndices[2]);
		}
		return new IndeciesArray(list, types);
	}
	
	public static class Builder {
		
		private final List<Vector3f> vertices;
		private final List<Vector2f> textureCoords;
		private final List<Vector3f> normals;
		private final List<Face> faces;
		
		public Builder() {
			this.vertices      = new ArrayList<>();
			this.textureCoords = new ArrayList<>();
			this.faces         = new ArrayList<>();
			this.normals       = new ArrayList<>();
		}
		
		public void addFaces(final Face build) {
			this.faces.add(build);
		}
		
		public void addNormals(final Vector3f normalize) {
			this.normals.add(normalize);
		}
		
		public void addTextureCoordinates(final Vector2f vector2f) {
			this.textureCoords.add(vector2f);
		}
		
		public void addVertices(final Vector3f vector3f) {
			this.vertices.add(vector3f);
		}
		
		public Mesh build() {
			return new Mesh(this.vertices, this.textureCoords, this.normals, this.faces);
		}
		
		public class VertexIndex {
			
			private final int vertex, normal, textureCoordinate;
			
			public VertexIndex(final int vertex, final int normal, final int textureCoordinate) {
				this.vertex            = vertex - 1;
				this.normal            = normal - 1;
				this.textureCoordinate = textureCoordinate - 1;
			}
			
			@Override
			public boolean equals(final Object obj) {
				if(this == obj) return true;
				if(!(obj instanceof VertexIndex)) return false;
				final VertexIndex other = (VertexIndex) obj;
				if(this.normal != other.normal) return false;
				if(this.textureCoordinate != other.textureCoordinate) return false;
				if(this.vertex != other.vertex) return false;
				return true;
			}
			
			public int getNormal() {
				return this.normal;
			}
			
			public int getTextureCoordinate() {
				return this.textureCoordinate;
			}
			
			public int getVertex() {
				return this.vertex;
			}
			
			@Override
			public int hashCode() {
				return Objects.hash(this.normal, this.textureCoordinate, this.vertex);
			}
			
			public boolean hasNormal() {
				return this.getNormal() >= 0;
			}
			
			public boolean hasTextureCoordinate() {
				return this.getTextureCoordinate() >= 0;
			}
			
			
		}
		
	}
	
	public static class Face {
		
		private final VertexIndex[] vertexIndices;
		
		public Face(final VertexIndex[] vertexIndices) {
			this.vertexIndices = vertexIndices;
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public boolean hasNormals() {
			for(final VertexIndex v : this.vertexIndices) if(!v.hasNormal()) return false;
			return true;
		}
		
		@Override
		public String toString() {
			return String.format("Face[vertexIndices=%s]",
				Arrays.toString(this.vertexIndices));
		}
		
		public static class Builder {
			
			private final VertexIndex[] vertexIndices;
			private int i;
			
			public Builder() {
				this.vertexIndices = new VertexIndex[3];
			}
			
			public Builder addVertex(final VertexIndex index) {
				if(this.i >= 3) throw new UnsupportedOperationException("quantity vertex is different from 3");
				this.vertexIndices[this.i++] = index;
				return Builder.this;
			}
			
			public Face build() {
				if(this.i != 3) throw new UnsupportedOperationException("quantity vertex is different from 3");
				return new Face(this.vertexIndices);
			}
		}
	}
	
	public class IndeciesArray {
		
		private final float[] vertices;
		private final int[] indecies;
		
		public IndeciesArray(final List<VertexIndex> list, final Type... types) {
			this.indecies = new int[list.size()];

//			for(int i = 0; i < indecies.length; i++) this.indecies[i] = i;
//			int size = 0;
//			for(final Type type : types) size += type.getSize();
//			this.vertices = new float[size * list.size()];
//			int i = 0;
//			for(final VertexIndex index : list)
//			for(final Type type : types) {
//				final float[] f = type.get(Mesh.this, index);
//				for(int k = 0; k < f.length; k++, i++) this.vertices[i] = f[k];
//			}
			
			
			final Map<VertexIndex, Integer> map = new HashMap<>(list.size());
			{
				int i = 0;
				for(final VertexIndex index : list) {
					Integer r = map.get(index);
					if(r == null) {
						map.put(index, i++);
						r = i;
					}
				}
			}
			for(int i = 0; i < indecies.length; i++) this.indecies[i] = map.get(list.get(i));
			{
				int size = 0;
				for(final Type type : types) size += type.getSize();
				this.vertices = new float[size * map.size()];
				int i = 0;
				for(final VertexIndex index : list) if(map.containsKey(index))
					for(final Type type : types) {
						final float[] f = type.get(Mesh.this, index);
						for(int k = 0; k < f.length; k++, i++) this.vertices[i] = f[k];
						map.remove(index);
					}
			}
			Log.info("load mesh IndeciesArray" + (vertices.length + indecies.length) * 4 + " bytes");
			System.gc();
		}
		
		public int[] getIndecies() {
			return this.indecies;
		}
		
		public float[] getVertex() {
			return this.vertices;
		}
		
	}
	
	public enum Type{
		
		VERTEX(3){
			
			@Override
			float[] get(final Mesh mesh, final VertexIndex index) {
				final var v = mesh.vertices.get(index.getVertex());
				return new float[]{v.x,v.y,v.z};
			}
			
		},
		NORMAL(3){
			
			@Override
			float[] get(final Mesh mesh, final VertexIndex index) {
				final var v = mesh.normals.get(index.getNormal());
				return new float[]{v.x,v.y,v.z};
			}
		},
		TEXTURE_COORDINAT(2){
			
			@Override
			float[] get(final Mesh mesh, final VertexIndex index) {
				final var v = mesh.textureCoords.get(index.getTextureCoordinate());
				return new float[]{v.x,v.y};
			}
		};
		
		private final int size;
		
		Type(final int size) {
			this.size = size;
		}
		
		abstract float[] get(Mesh mesh, VertexIndex index);
		
		int getSize() {
			return this.size;
		}
	}
	
}
/*

private float[] getVertexAndTexCoordAndNormal(List<Vector3f> vertices, List<Vector2f> textureCoords, List<Vector3f> normals, List<Face> faces) {
	final float[] mas = new float[faces.size() * 3 * 8];
	for(int i = 0; i < faces.size(); i++) {
		final Face face = faces.get(i);
		mas[24 * i]     = vertices.get(face.vertexIndices[0]).x;
		mas[24 * i + 1] = vertices.get(face.vertexIndices[0]).x;
		mas[24 * i + 2] = vertices.get(face.vertexIndices[0]).z;
		mas[24 * i + 3] = textureCoords.get(face.textureCoordinateIndices[0]).x;
		mas[24 * i + 4] = textureCoords.get(face.textureCoordinateIndices[0]).y;
		mas[24 * i + 5] = normals.get(face.normalIndices[0]).x;
		mas[24 * i + 6] = normals.get(face.normalIndices[0]).y;
		mas[24 * i + 7] = normals.get(face.normalIndices[0]).z;
		
		mas[24 * i + 8]  = vertices.get(face.vertexIndices[1]).x;
		mas[24 * i + 9]  = vertices.get(face.vertexIndices[1]).x;
		mas[24 * i + 10] = vertices.get(face.vertexIndices[1]).z;
		mas[24 * i + 11] = textureCoords.get(face.textureCoordinateIndices[1]).x;
		mas[24 * i + 12] = textureCoords.get(face.textureCoordinateIndices[1]).y;
		mas[24 * i + 13] = normals.get(face.normalIndices[1]).x;
		mas[24 * i + 14] = normals.get(face.normalIndices[1]).y;
		mas[24 * i + 15] = normals.get(face.normalIndices[1]).z;
		
		mas[24 * i + 16] = vertices.get(face.vertexIndices[2]).x;
		mas[24 * i + 17] = vertices.get(face.vertexIndices[2]).x;
		mas[24 * i + 18] = vertices.get(face.vertexIndices[2]).z;
		mas[24 * i + 19] = textureCoords.get(face.textureCoordinateIndices[2]).x;
		mas[24 * i + 20] = textureCoords.get(face.textureCoordinateIndices[2]).y;
		mas[24 * i + 21] = normals.get(face.normalIndices[2]).x;
		mas[24 * i + 22] = normals.get(face.normalIndices[2]).y;
		mas[24 * i + 23] = normals.get(face.normalIndices[2]).z;
	}
	return mas;
}

	private static float[] getVertex(List<Vector3f> vertices, List<Face> faces) {
		final float[] mas = new float[faces.size() * 9];
		for(int i = 0; i < faces.size(); i++) {
			final VertexIndex[] face = faces.get(i).vertexIndices;
			mas[9 * i]     = vertices.get(face[0].getVertex()).x;
			mas[9 * i + 1] = vertices.get(face[0].getVertex()).y;
			mas[9 * i + 2] = vertices.get(face[0].getVertex()).z;
			mas[9 * i + 3] = vertices.get(face[1].getVertex()).x;
			mas[9 * i + 4] = vertices.get(face[1].getVertex()).y;
			mas[9 * i + 5] = vertices.get(face[1].getVertex()).z;
			mas[9 * i + 6] = vertices.get(face[2].getVertex()).x;
			mas[9 * i + 7] = vertices.get(face[2].getVertex()).y;
			mas[9 * i + 8] = vertices.get(face[2].getVertex()).z;
		}
		return mas;
	}
	
	private static float[] getVertexAndNormal(List<Vector3f> vertices, List<Vector3f> normals, List<Face> faces) {
		final float[] mas = new float[faces.size() * 18];
		for(int i = 0; i < faces.size(); i++) {
			final VertexIndex[] face = faces.get(i).vertexIndices;
			mas[18 * i]      = vertices.get(face[0].getVertex()).x;
			mas[18 * i + 1]  = vertices.get(face[0].getVertex()).y;
			mas[18 * i + 2]  = vertices.get(face[0].getVertex()).z;
			mas[18 * i + 3]  = normals.get(face[0]).x;
			mas[18 * i + 4]  = normals.get(face[0]).y;
			mas[18 * i + 5]  = normals.get(face[0]).z;
			mas[18 * i + 6]  = vertices.get(face[1].getVertex()).x;
			mas[18 * i + 7]  = vertices.get(face[1].getVertex()).y;
			mas[18 * i + 8]  = vertices.get(face.vertexIndices[1].getVertex()).z;
			mas[18 * i + 9]  = normals.get(face.normalIndices[1]).x;
			mas[18 * i + 10] = normals.get(face.normalIndices[1]).y;
			mas[18 * i + 11] = normals.get(face.normalIndices[1]).z;
			mas[18 * i + 12] = vertices.get(face.vertexIndices[2].getVertex()).x;
			mas[18 * i + 13] = vertices.get(face.vertexIndices[2].getVertex()).y;
			mas[18 * i + 14] = vertices.get(face.vertexIndices[2].getVertex()).z;
			mas[18 * i + 15] = normals.get(face.normalIndices[2]).x;
			mas[18 * i + 16] = normals.get(face.normalIndices[2]).y;
			mas[18 * i + 17] = normals.get(face.normalIndices[2]).z;
		}
		return mas;
	}
*/
