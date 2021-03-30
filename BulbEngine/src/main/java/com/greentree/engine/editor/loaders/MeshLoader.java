package com.greentree.engine.editor.loaders;

import com.greentree.engine.editor.AbstractLoader;
import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
public class MeshLoader extends AbstractLoader<Mesh> {
	
	public MeshLoader() {
		super(Mesh.class);
	}
	
	@Override
	public Mesh load(final String value) {
		return com.greentree.engine.mesh.MeshLoader.load(value);
	}
}
