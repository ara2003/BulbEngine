package com.greentree.engine2d.physics;

import com.greentree.common.math.vector.Vector2f;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine2d.collizion.ColliderComponent;

@RequireComponent({ColliderComponent.class})
public class Physics2DComponent extends GameComponent {

	@EditorData
	private float mass = 1;
	private Vector2f vel = new Vector2f(0);

	public Vector2f getVelosity() {
		return vel;
	}

}
