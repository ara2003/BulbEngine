package com.greentree.engine.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

@SuppressWarnings("exports")
public class Mesh extends Object {
	
	private final List<Vector3f> vertices;
	private final List<Vector2f> textureCoords;
	private final List<Vector3f> normals;
	private final List<Face> faces;
	private boolean enableSmoothShading;
	
	public Mesh() {
		this(new ArrayList<Vector3f>(), new ArrayList<Vector2f>(), new ArrayList<Vector3f>(), new ArrayList<Face>(),
			true);
	}
	
	public Mesh(final List<Vector3f> vertices, final List<Vector2f> textureCoords, final List<Vector3f> normals, final List<Face> faces,
		final boolean enableSmoothShading) {
		this.vertices            = vertices;
		this.textureCoords       = textureCoords;
		this.normals             = normals;
		this.faces               = faces;
		this.enableSmoothShading = enableSmoothShading;
	}
	
	public int getAmountVertices() {
		return this.faces.size() * 3;
	}
	
	public List<Face> getFaces() {
		return this.faces;
	}
	
	public int[] getIndecies() {
		final int[] mas = new int[this.faces.size() * 3];
//		for(short i = 0; i < this.faces.size(); i++) for(short j = 0; j < 3; j++) mas[3 * i + j] = this.faces.get(i).normalIndices[j];
		for(short i = 0; i < mas.length; i++) mas[i] = i;
		return mas;
	}
	
	public List<Vector3f> getNormals() {
		return this.normals;
	}
	
	public List<Vector2f> getTextureCoordinates() {
		return this.textureCoords;
	}
	
	public float[] getVertex() {
		final float[] mas = new float[this.faces.size() * 9];
		for(int i = 0; i < this.faces.size(); i++) {
			final int[] face = this.faces.get(i).vertexIndices;
			mas[9 * i]     = this.vertices.get(face[0]).x;
			mas[9 * i + 1] = this.vertices.get(face[0]).y;
			mas[9 * i + 2] = this.vertices.get(face[0]).z;
			mas[9 * i + 3] = this.vertices.get(face[1]).x;
			mas[9 * i + 4] = this.vertices.get(face[1]).y;
			mas[9 * i + 5] = this.vertices.get(face[1]).z;
			mas[9 * i + 6] = this.vertices.get(face[2]).x;
			mas[9 * i + 7] = this.vertices.get(face[2]).y;
			mas[9 * i + 8] = this.vertices.get(face[2]).z;
		}
		return mas;
	}
	
	public float[] getVertexAndNormal() {
		final float[] mas = new float[this.faces.size() * 18];
		for(int i = 0; i < this.faces.size(); i++) {
			final Face face = this.faces.get(i);
			mas[18 * i]      = this.vertices.get(face.vertexIndices[0]).x;
			mas[18 * i + 1]  = this.vertices.get(face.vertexIndices[0]).y;
			mas[18 * i + 2]  = this.vertices.get(face.vertexIndices[0]).z;
			mas[18 * i + 3]  = this.normals.get(face.normalIndices[0]).x;
			mas[18 * i + 4]  = this.normals.get(face.normalIndices[0]).y;
			mas[18 * i + 5]  = this.normals.get(face.normalIndices[0]).z;
			mas[18 * i + 6]  = this.vertices.get(face.vertexIndices[1]).x;
			mas[18 * i + 7]  = this.vertices.get(face.vertexIndices[1]).y;
			mas[18 * i + 8]  = this.vertices.get(face.vertexIndices[1]).z;
			mas[18 * i + 9]  = this.normals.get(face.normalIndices[1]).x;
			mas[18 * i + 10] = this.normals.get(face.normalIndices[1]).y;
			mas[18 * i + 11] = this.normals.get(face.normalIndices[1]).z;
			mas[18 * i + 12] = this.vertices.get(face.vertexIndices[2]).x;
			mas[18 * i + 13] = this.vertices.get(face.vertexIndices[2]).y;
			mas[18 * i + 14] = this.vertices.get(face.vertexIndices[2]).z;
			mas[18 * i + 15] = this.normals.get(face.normalIndices[2]).x;
			mas[18 * i + 16] = this.normals.get(face.normalIndices[2]).y;
			mas[18 * i + 17] = this.normals.get(face.normalIndices[2]).z;
		}
		return mas;
	}
	
	public float[] getVertexAndTexCoordAndNormal() {
		final float[] mas = new float[this.faces.size() * 3 * 8];
		for(int i = 0; i < this.faces.size(); i++) {
			final Face face = this.faces.get(i);
			mas[24 * i]     = this.vertices.get(face.vertexIndices[0]).x;
			mas[24 * i + 1] = this.vertices.get(face.vertexIndices[0]).x;
			mas[24 * i + 2] = this.vertices.get(face.vertexIndices[0]).z;
			mas[24 * i + 3] = this.textureCoords.get(face.textureCoordinateIndices[0]).x;
			mas[24 * i + 4] = this.textureCoords.get(face.textureCoordinateIndices[0]).y;
			mas[24 * i + 5] = this.normals.get(face.normalIndices[0]).x;
			mas[24 * i + 6] = this.normals.get(face.normalIndices[0]).y;
			mas[24 * i + 7] = this.normals.get(face.normalIndices[0]).z;
			
			mas[24 * i + 8]  = this.vertices.get(face.vertexIndices[1]).x;
			mas[24 * i + 9]  = this.vertices.get(face.vertexIndices[1]).x;
			mas[24 * i + 10] = this.vertices.get(face.vertexIndices[1]).z;
			mas[24 * i + 11] = this.textureCoords.get(face.textureCoordinateIndices[1]).x;
			mas[24 * i + 12] = this.textureCoords.get(face.textureCoordinateIndices[1]).y;
			mas[24 * i + 13] = this.normals.get(face.normalIndices[1]).x;
			mas[24 * i + 14] = this.normals.get(face.normalIndices[1]).y;
			mas[24 * i + 15] = this.normals.get(face.normalIndices[1]).z;
			
			mas[24 * i + 16] = this.vertices.get(face.vertexIndices[2]).x;
			mas[24 * i + 17] = this.vertices.get(face.vertexIndices[2]).x;
			mas[24 * i + 18] = this.vertices.get(face.vertexIndices[2]).z;
			mas[24 * i + 19] = this.textureCoords.get(face.textureCoordinateIndices[2]).x;
			mas[24 * i + 20] = this.textureCoords.get(face.textureCoordinateIndices[2]).y;
			mas[24 * i + 21] = this.normals.get(face.normalIndices[2]).x;
			mas[24 * i + 22] = this.normals.get(face.normalIndices[2]).y;
			mas[24 * i + 23] = this.normals.get(face.normalIndices[2]).z;
		}
		return mas;
	}
	
	public List<Vector3f> getVertices() {
		return this.vertices;
	}
	
	public boolean hasNormals() {
		return !this.normals.isEmpty();
	}
	
	public boolean hasTextureCoordinates() {
		return !this.textureCoords.isEmpty();
	}
	
	public boolean isSmoothShadingEnabled() {
		return this.enableSmoothShading;
	}
	
	public void setSmoothShadingEnabled(final boolean isSmoothShadingEnabled) {
		this.enableSmoothShading = isSmoothShadingEnabled;
	}
	
	@Override
	public String toString() {
		return "Mesh [vertices=" + this.vertices + ", textureCoords=" + this.textureCoords + ", normals=" + this.normals + ", faces="
			+ this.faces + ", enableSmoothShading=" + this.enableSmoothShading + "]";
	}
	
	public static class Face {
		
		private final int[] vertexIndices;
		private final int[] normalIndices;
		private final int[] textureCoordinateIndices;
		
		public Face(final int[] vertexIndices, final int[] textureCoordinateIndices, final int[] normalIndices) {
			for(int i = 0; i < vertexIndices.length; i++) vertexIndices[i]--;
			if(textureCoordinateIndices != null)
				for(int i = 0; i < textureCoordinateIndices.length; i++) textureCoordinateIndices[i]--;
			if(normalIndices != null) for(int i = 0; i < normalIndices.length; i++) normalIndices[i]--;
			this.vertexIndices            = vertexIndices;
			this.normalIndices            = normalIndices;
			this.textureCoordinateIndices = textureCoordinateIndices;
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public int[] getNormals() {
			return this.normalIndices;
		}
		
		public int[] getTextureCoords() {
			return this.textureCoordinateIndices;
		}
		
		public int[] getVertices() {
			return this.vertexIndices;
		}
		
		public boolean hasNormals() {
			return this.normalIndices != null;
		}
		
		public boolean hasTextureCoords() {
			return this.textureCoordinateIndices != null;
		}
		
		@Override
		public String toString() {
			return String.format("Face[vertexIndices%s normalIndices%s textureCoordinateIndices%s]",
				Arrays.toString(this.vertexIndices), Arrays.toString(this.normalIndices),
				Arrays.toString(this.textureCoordinateIndices));
		}
		
		public static class Builder {
			
			private int[] vertexIndices;
			private int[] normalIndices, textureCoordinateIndices;
			
			public Face build() {
				return new Face(this.vertexIndices, this.textureCoordinateIndices, this.normalIndices);
			}
			
			public Builder setNormal(final int[] s) {
				this.normalIndices = s;
				return Builder.this;
			}
			
			public Builder setTextureCoordinate(final int[] s) {
				this.textureCoordinateIndices = s;
				return Builder.this;
			}
			
			public Builder setVertex(final int[] s) {
				this.vertexIndices = s;
				return Builder.this;
			}
		}
	}
}
