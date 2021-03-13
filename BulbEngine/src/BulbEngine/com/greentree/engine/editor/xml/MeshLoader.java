package com.greentree.engine.editor.xml;

import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
public class MeshLoader extends ClassLoader<Mesh> {
	
	@Override
	public Mesh load(String value) {
		return com.greentree.engine.mesh.MeshLoader.load(value);
	}
}
