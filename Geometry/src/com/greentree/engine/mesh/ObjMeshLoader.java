package com.greentree.engine.mesh;

import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.greentree.common.Log;
import com.greentree.common.loading.ResourceLoader;

/**
 * @author Arseny Latyshev
 *
 */
public class ObjMeshLoader implements MeshLoaderI {
	
	@Override
	public Mesh load(String resourse) {
		if(!resourse.endsWith(".obj"))return null;
		Mesh mesh = new Mesh();
		Scanner sc = new Scanner(ResourceLoader.getResourceAsStream(resourse));
		while (sc.hasNextLine()) {
			String ln = sc.nextLine();
			if ((ln == null) || ln.equals("") || ln.startsWith("#")) {
			} else {
				String[] split = ln.split(" ");
				switch (split[0]) {
					case "v":
						mesh.getVertices().add(new Vector3f(
								Float.parseFloat(split[1]),
								Float.parseFloat(split[2]),
								Float.parseFloat(split[3])
								));
						break;
					case "vn":
						mesh.getNormals().add(new Vector3f(
							Float.parseFloat(split[1]),
							Float.parseFloat(split[2]),
							Float.parseFloat(split[3])
						).normalize());
						break;
					case "vt":
						mesh.getTextureCoordinates().add(new Vector2f(
								Float.parseFloat(split[1]),
								Float.parseFloat(split[2])
								));
						break;
					case "f":
						Mesh.Face.Builder buider = Mesh.Face.builder().setVertex(new int[]{
							Integer.parseInt(split[1].split("/")[0]),
							Integer.parseInt(split[2].split("/")[0]),
							Integer.parseInt(split[3].split("/")[0])
						});
						if(mesh.hasTextureCoordinates()) {
							buider.setTextureCoordinate(new int[]{
								Integer.parseInt(split[1].split("/")[1]),
								Integer.parseInt(split[2].split("/")[1]),
								Integer.parseInt(split[3].split("/")[1])
							});
						}
						if(mesh.hasNormals()) {
							buider.setNormal(new int[]{
								Integer.parseInt(split[1].split("/")[2]),
								Integer.parseInt(split[2].split("/")[2]),
								Integer.parseInt(split[3].split("/")[2])
							});
						}
						mesh.getFaces().add(buider.build());
						break;
					case "s":
						mesh.setSmoothShadingEnabled(!ln.contains("off"));
						break;
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
		if(mesh.getVertices().isEmpty())return null;
		return mesh;
	}
	
}
