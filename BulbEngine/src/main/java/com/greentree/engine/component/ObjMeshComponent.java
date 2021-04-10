package com.greentree.engine.component;

import com.greentree.engine.builder.loaders.ObjMeshLoader;
import com.greentree.engine.builder.xml.NecessarilyLoaders;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
public class ObjMeshComponent extends AbstractMeshComponent {
	
	@NecessarilyLoaders(ObjMeshLoader.class)
	@EditorData
	private Mesh mesh;
	
	@Override
	public Mesh getMesh() {
		return this.mesh;
	}
	
}
