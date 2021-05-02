package com.greentree.engine.component;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.object.GameObject;

/** @author Arseny Latyshev */
@RequireComponent({ColliderComponent.class})
public abstract class CollisionGameComponent extends GameComponent {
	
	public void CollisionEnter(final GameObject object) {
	}
	
	public void CollisionExit(final GameObject object) {
	}
	
	public void CollisionStay(final GameObject object) {
	}
	
	@Override
	public void start() {
		for(final ColliderComponent col : this.getObject().getAllComponents(ColliderComponent.class)) {
			col.getAction().addEnterObjectListener(this::CollisionEnter);
			col.getAction().addExitObjectListener(this::CollisionExit);
			col.getAction().addStayObjectListener(this::CollisionStay);
		}
	}
}
