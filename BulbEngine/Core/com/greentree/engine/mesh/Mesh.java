package com.greentree.engine.mesh;

import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;

import java.util.ArrayList;
import java.util.Arrays;
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew 'siD' Van der Bijl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

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
	
	public Mesh(List<Vector3f> vertices, List<Vector2f> textureCoords, List<Vector3f> normals, List<Face> faces,
			boolean enableSmoothShading) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.faces = faces;
		this.enableSmoothShading = enableSmoothShading;
	}
	
	public void enableStates() {
		if(isSmoothShadingEnabled()) {
			GL11.glShadeModel(GL_SMOOTH);
		}else {
			GL11.glShadeModel(GL_FLAT);
		}
	}
	
	public int getAmountVertices() {
		return faces.size() * 3;
	}
	
	public List<Face> getFaces() {
		return faces;
	}
	
	public short[] getIndecies() {
		short[] mas = new short[faces.size() * 3];
		//		for(short i = 0; i < faces.size(); i++) {
		//			for(short j = 0; j < 3; j++) mas[3 * i + j] = faces.get(i).vertexIndices[j];
		//		}
		for(short i = 0; i < mas.length; i++) mas[i] = i;
		return mas;
	}
	
	public List<Vector3f> getNormals() {
		return normals;
	}
	
	public List<Vector2f> getTextureCoordinates() {
		return textureCoords;
	}
	
	public float[] getVertex() {
		float[] mas = new float[faces.size() * 9];
		for(int i = 0; i < faces.size(); i++) {
			int[] face = faces.get(i).vertexIndices;
			mas[9 * i] = vertices.get(face[0]).x;
			mas[9 * i + 1] = vertices.get(face[0]).y;
			mas[9 * i + 2] = vertices.get(face[0]).z;
			mas[9 * i + 3] = vertices.get(face[1]).x;
			mas[9 * i + 4] = vertices.get(face[1]).y;
			mas[9 * i + 5] = vertices.get(face[1]).z;
			mas[9 * i + 6] = vertices.get(face[2]).x;
			mas[9 * i + 7] = vertices.get(face[2]).y;
			mas[9 * i + 8] = vertices.get(face[2]).z;
		}
		return mas;
	}
	
	public float[] getVertexAndNormal() {
		float[] mas = new float[faces.size() * 18];
		for(int i = 0; i < faces.size(); i++) {
			Face face = faces.get(i);
			mas[18 * i] = vertices.get(face.vertexIndices[0]).x;
			mas[18 * i + 1] = vertices.get(face.vertexIndices[0]).y;
			mas[18 * i + 2] = vertices.get(face.vertexIndices[0]).z;
			mas[18 * i + 3] = normals.get(face.normalIndices[0]).x;
			mas[18 * i + 4] = normals.get(face.normalIndices[0]).y;
			mas[18 * i + 5] = normals.get(face.normalIndices[0]).z;
			mas[18 * i + 6] = vertices.get(face.vertexIndices[1]).x;
			mas[18 * i + 7] = vertices.get(face.vertexIndices[1]).y;
			mas[18 * i + 8] = vertices.get(face.vertexIndices[1]).z;
			mas[18 * i + 9] = normals.get(face.normalIndices[1]).x;
			mas[18 * i + 10] = normals.get(face.normalIndices[1]).y;
			mas[18 * i + 11] = normals.get(face.normalIndices[1]).z;
			mas[18 * i + 12] = vertices.get(face.vertexIndices[2]).x;
			mas[18 * i + 13] = vertices.get(face.vertexIndices[2]).y;
			mas[18 * i + 14] = vertices.get(face.vertexIndices[2]).z;
			mas[18 * i + 15] = normals.get(face.normalIndices[2]).x;
			mas[18 * i + 16] = normals.get(face.normalIndices[2]).y;
			mas[18 * i + 17] = normals.get(face.normalIndices[2]).z;
		}
		return mas;
	}
	
	public float[] getVertexAndTexCoordAndNormal() {
		float[] mas = new float[faces.size() * 3 * 8];
		for(int i = 0; i < faces.size(); i++) {
			Face face = faces.get(i);
			mas[24 * i] = vertices.get(face.vertexIndices[0]).x;
			mas[24 * i + 1] = vertices.get(face.vertexIndices[0]).x;
			mas[24 * i + 2] = vertices.get(face.vertexIndices[0]).z;
			mas[24 * i + 3] = textureCoords.get(face.textureCoordinateIndices[0]).x;
			mas[24 * i + 4] = textureCoords.get(face.textureCoordinateIndices[0]).y;
			mas[24 * i + 5] = normals.get(face.normalIndices[0]).x;
			mas[24 * i + 6] = normals.get(face.normalIndices[0]).y;
			mas[24 * i + 7] = normals.get(face.normalIndices[0]).z;
			
			mas[24 * i + 8] = vertices.get(face.vertexIndices[1]).x;
			mas[24 * i + 9] = vertices.get(face.vertexIndices[1]).x;
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
	
	public List<Vector3f> getVertices() {
		return vertices;
	}
	
	public boolean hasNormals() {
		return !normals.isEmpty();
	}
	
	public boolean hasTextureCoordinates() {
		return !textureCoords.isEmpty();
	}
	
	public boolean isSmoothShadingEnabled() {
		return enableSmoothShading;
	}
	
	public void setSmoothShadingEnabled(boolean isSmoothShadingEnabled) {
		enableSmoothShading = isSmoothShadingEnabled;
	}
	
	@Override
	public String toString() {
		return "Mesh [vertices=" + vertices + ", textureCoords=" + textureCoords + ", normals=" + normals + ", faces="
				+ faces + ", enableSmoothShading=" + enableSmoothShading + "]";
	}
	
	public static class Face {
		
		private final int[] vertexIndices;
		private final int[] normalIndices;
		private final int[] textureCoordinateIndices;
		
		public Face(int[] vertexIndices, int[] textureCoordinateIndices, int[] normalIndices) {
			for(int i = 0; i < vertexIndices.length; i++) vertexIndices[i]--;
			if(textureCoordinateIndices != null)
				for(int i = 0; i < textureCoordinateIndices.length; i++) textureCoordinateIndices[i]--;
			if(normalIndices != null) for(int i = 0; i < normalIndices.length; i++) normalIndices[i]--;
			this.vertexIndices = vertexIndices;
			this.normalIndices = normalIndices;
			this.textureCoordinateIndices = textureCoordinateIndices;
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public int[] getNormals() {
			return normalIndices;
		}
		
		public int[] getTextureCoords() {
			return textureCoordinateIndices;
		}
		
		public int[] getVertices() {
			return vertexIndices;
		}
		
		public boolean hasNormals() {
			return normalIndices != null;
		}
		
		public boolean hasTextureCoords() {
			return textureCoordinateIndices != null;
		}
		
		@Override
		public String toString() {
			return String.format("Face[vertexIndices%s normalIndices%s textureCoordinateIndices%s]",
					Arrays.toString(vertexIndices), Arrays.toString(normalIndices),
					Arrays.toString(textureCoordinateIndices));
		}
		
		public static class Builder {
			
			private int[] vertexIndices, normalIndices, textureCoordinateIndices;
			
			public Face build() {
				return new Face(vertexIndices, textureCoordinateIndices, normalIndices);
			}
			
			public Builder setNormal(int[] s) {
				normalIndices = s;
				return Builder.this;
			}
			
			public Builder setTextureCoordinate(int[] s) {
				textureCoordinateIndices = s;
				return Builder.this;
			}
			
			public Builder setVertex(int[] s) {
				vertexIndices = s;
				return Builder.this;
			}
		}
	}
}
