package com.greentree.engine.mesh;

import java.util.ArrayList;
import java.util.List;

import com.greentree.util.Log;

/**
 * @author Arseny Latyshev
 *
 */
public class MeshLoader {
	
	private final static List<MeshLoaderI> loaders = new ArrayList<>();
	
	private MeshLoader() {
	}
	
	public static void addLoader(MeshLoaderI loader){
		loaders.add(loader);
	}
	
	public static Mesh load(String resource) {
		Mesh mesh = null;
		for(MeshLoaderI loader : loaders) {
			try {
				mesh = loader.load(resource);
			}catch(Exception e) {
				Log.warn(e);
			}
			if(mesh != null)break;
		}
		return mesh;
	}
	
}
