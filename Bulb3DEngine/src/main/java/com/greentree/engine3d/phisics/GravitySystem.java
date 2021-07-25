package com.greentree.engine3d.phisics;

import com.greentree.common.math.vector.Vector3f;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;


public class GravitySystem extends MultiBehaviour {
		
	@Required
	@EditorData
	private Vector3f gravity;
	
	@Override
	public void update() {
		for(var c : getAllComponents(GravitationalComponent.class))c.getComponent(Transform.class).position.add(gravity);
	}
	
	
}
