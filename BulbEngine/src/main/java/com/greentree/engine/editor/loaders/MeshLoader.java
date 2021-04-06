package com.greentree.engine.editor.loaders;

import java.util.Objects;

import com.greentree.engine.mesh.Mesh;
import com.greentree.engine.mesh.ObjMeshLoader;

/** @author Arseny Latyshev */
public class MeshLoader extends AbstractLoader<Mesh> {
	
	static {
		com.greentree.engine.mesh.MeshLoader.addLoader(new ObjMeshLoader());
	}
	
	public MeshLoader() {
		super(Mesh.class);
		
	}
	
	@Override
	public Mesh load(final String value) {
		return Objects.requireNonNull(com.greentree.engine.mesh.MeshLoader.load(value));
	}
}
