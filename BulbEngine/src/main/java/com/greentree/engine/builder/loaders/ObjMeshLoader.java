package com.greentree.engine.builder.loaders;

import java.util.Scanner;

import com.greentree.common.logger.Log;
import com.greentree.common.math.vector.Vector2f;
import com.greentree.common.math.vector.Vector3f;
import com.greentree.data.loaders.value.CachingAbstractLoader;
import com.greentree.data.loading.ResourceLoader;
import com.greentree.engine.mesh.Mesh;
import com.greentree.engine.mesh.Mesh.Builder;
import com.greentree.engine.mesh.Mesh.Builder.VertexIndex;

/** @author Arseny Latyshev */
public class ObjMeshLoader extends CachingAbstractLoader {

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
		return mesh.new VertexIndex(v, n, t);
	}

	@Override
	public Mesh load0(final String resourse) {
		if(!resourse.endsWith(".obj")) throw new IllegalArgumentException(resourse);
		mesh = Mesh.builder();
		final Scanner sc = new Scanner(ResourceLoader.getResourceAsStream(resourse));
		while(sc.hasNextLine()) {
			final String ln = sc.nextLine();
			if(ln == null || "".equals(ln) || ln.startsWith("#")) {
			}else {
				final String[] split = ln.replace("  ", " ").split(" ");
				switch(split[0]) {
					case "v":
						mesh.addVertices(new Vector3f(
								Float.parseFloat(split[1]),
								Float.parseFloat(split[2]),
								Float.parseFloat(split[3])));
						break;
					case "vn":
						mesh.addNormals(new Vector3f(
								Float.parseFloat(split[1]),
								Float.parseFloat(split[2]),
								Float.parseFloat(split[3])).normalize());
						break;
					case "vt":
						mesh.addTextureCoordinates(new Vector2f(
								Float.parseFloat(split[1]),
								Float.parseFloat(split[2])));
						break;
					case "f":
						mesh.addFaces(Mesh.Face.builder().addVertex(getVertex(split[1])).addVertex(getVertex(split[2])).addVertex(getVertex(split[3])).build());
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
		final Mesh mesh0 = mesh.build();
		System.gc();
		return mesh0;
	}

}
