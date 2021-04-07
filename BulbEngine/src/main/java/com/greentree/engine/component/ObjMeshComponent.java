package com.greentree.engine.component;

import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.editor.loaders.MainLoader;
import com.greentree.engine.editor.loaders.NecessarilyLoaders;
import com.greentree.engine.editor.loaders.ObjMeshLoader;
import com.greentree.engine.mesh.Mesh;

/**
 * @author Arseny Latyshev
 *
 */
public class ObjMeshComponent extends AbstractMeshComponent {
	
	@NecessarilyLoaders(ObjMeshLoader.class)
	@EditorData
	private Mesh mesh;
	
	public Mesh getMesh() {
		return mesh;
	}
	
}
