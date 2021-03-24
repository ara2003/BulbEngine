package com.greentree.engine.component;

import com.greentree.engine.collizion.CollisionListener;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.object.GameObject;

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
		addListener(new CollisionListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionEnter(ColliderComponent object1, ColliderComponent object2) {
				if(getObject().equals(object1.getObject()))CollisionGameComponent.this.CollisionEnter(object2.getObject());
			}

			@Override
			public void CollisionExit(ColliderComponent object1, ColliderComponent object2) {
				if(getObject().equals(object1.getObject()))CollisionGameComponent.this.CollisionExit(object2.getObject());
			}

			@Override
			public void CollisionStay(ColliderComponent object1, ColliderComponent object2) {
				if(getObject().equals(object1.getObject()))CollisionGameComponent.this.CollisionStay(object2.getObject());
			}
			
		});
	}
}
