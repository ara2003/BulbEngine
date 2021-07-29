package com.greentree.engine2d.collizion;

import com.greentree.engine.component.StartGameComponent;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.node.GameObject;

/** @author Arseny Latyshev */
@RequireComponent({ColliderComponent.class})
public abstract class CollisionGameComponent extends StartGameComponent {

	public void CollisionEnter(final GameObject object) {
	}

	public void CollisionExit(final GameObject object) {
	}

	public void CollisionStay(final GameObject object) {
	}

	@Override
	public void start() {
		for(final ColliderComponent col : getObject().getAllComponents(ColliderComponent.class)) {
			col.getAction().addEnterObjectListener(this::CollisionEnter);
			col.getAction().addExitObjectListener(this::CollisionExit);
			col.getAction().addStayObjectListener(this::CollisionStay);
		}
	}
}
