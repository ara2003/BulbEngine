package com.greentree.engine.component;

import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.mesh.Mesh;


/**
 * @author Arseny Latyshev
 *
 */
public class MeshComponent extends GameComponent {
	
	@EditorData
	private Mesh mesh;
	
	public Mesh getMesh() {
		return mesh;
	}
	
	
	
}
