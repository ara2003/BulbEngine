package com.greentree.engine.component;

import com.greentree.data.loaders.NecessarilyLoaders;
import com.greentree.engine.builder.loaders.ObjMeshLoader;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
@NecessarilyLoaders(ObjMeshLoader.class)
public class ObjMeshComponent extends AbstractMeshComponent {
	
	@EditorData
	private Mesh mesh;
	
	@Override
	public Mesh getMesh() {
		return this.mesh;
	}
	
}
