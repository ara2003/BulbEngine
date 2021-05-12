package com.greentree.engine.builder.loaders;

import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.builder.loaders.CachingAbstractLoader;
import com.greentree.engine.mesh.Mesh;
import com.greentree.engine.mesh.Mesh.Builder;
import com.greentree.engine.mesh.Mesh.Builder.VertexIndex;

/** @author Arseny Latyshev */
public class ObjMeshLoader extends CachingAbstractLoader<Mesh> {
	
	private Builder mesh;
	
	public ObjMeshLoader() {
		super(Mesh.class);
	}
	
	private VertexIndex getVertex(final String string) {
		final String[] index = string.replace(" ", "").split("/");
		final int      v     = Integer.parseInt(index[0]);
		int            t     = 0;
		if(index.length > 1) t = index[1] == "" ? -1 : Integer.parseInt(index[1]);
		int n = 0;
		if(index.length > 2) n = index[2] == "" ? -1 : Integer.parseInt(index[2]);
		return this.mesh.new VertexIndex(v, n, t);
	}
	
	@Override
	public Mesh load0(final String resourse) {
		if(!resourse.endsWith(".obj")) throw new IllegalArgumentException(resourse);
		this.mesh = Mesh.builder();
		final Scanner sc = new Scanner(ResourceLoader.getResourceAsStream(resourse));
		while(sc.hasNextLine()) {
			final String ln = sc.nextLine();
			if(ln == null || ln.equals("") || ln.startsWith("#")) {
			}else {
				final String[] split = ln.replaceAll("  ", " ").split(" ");
				switch(split[0]) {
					case "v":
						this.mesh.addVertices(new Vector3f(
							Float.parseFloat(split[1]),
							Float.parseFloat(split[2]),
							Float.parseFloat(split[3])));
					break;
					case "vn":
						this.mesh.addNormals(new Vector3f(
							Float.parseFloat(split[1]),
							Float.parseFloat(split[2]),
							Float.parseFloat(split[3])).normalize());
					break;
					case "vt":
						this.mesh.addTextureCoordinates(new Vector2f(
							Float.parseFloat(split[1]),
							Float.parseFloat(split[2])));
					break;
					case "f":
						this.mesh.addFaces(Mesh.Face.builder().addVertex(this.getVertex(split[1])).addVertex(this.getVertex(split[2])).addVertex(this.getVertex(split[3])).build());
					break;
					case "s":
					case "o":
					case "g":
					case "l":
					case "usemtl":
					case "mtllib":
					break;
					default:
						Log.warn(ObjMeshLoader.class + " Unknown Line: " + ln);
						return null;
				}
			}
		}
		sc.close();
		final Mesh mesh0 = this.mesh.build();
		System.gc();
		return mesh0;
	}
	
}
