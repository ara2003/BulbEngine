package com.greentree.engine.physics;

import org.joml.Vector2f;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.object.GameComponent;

@RequireComponent({ColliderComponent.class})
public class Physics2DComponent extends GameComponent {

	@EditorData
	private float mass = 1;
	private Vector2f vel = new Vector2f(0);

	public Vector2f getVelosity() {
		return vel;
	}

}
