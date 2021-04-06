package com.greentree.engine.component;

import com.greentree.engine.core.GameComponent;
import com.greentree.engine.mesh.Mesh;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class AbstractMeshComponent extends GameComponent {
	
	public abstract Mesh getMesh();
	
}
