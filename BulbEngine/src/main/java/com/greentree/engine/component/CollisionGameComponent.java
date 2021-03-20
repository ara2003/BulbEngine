package com.greentree.engine.component;

import com.greentree.engine.collizion.ObjectCollisionEnterListener;
import com.greentree.engine.collizion.OnceObjectCollisionExitListener;
import com.greentree.engine.collizion.ObjectCollisionStayListener;
import com.greentree.engine.collizion.OnceObjectCollisionListener;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameElement;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
@RequireComponent({ColliderComponent.class})
public abstract class CollisionGameComponent extends GameComponent
		implements ObjectCollisionEnterListener,
		ObjectCollisionStayListener, OnceObjectCollisionExitListener {
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void CollisionEnter(final GameObject object) {
	}
	
	
	@Override
	public void CollisionExit(final GameObject object) {
	}
	
	@Override
	public void CollisionStay(final GameObject object) {
	}
	
	@Override
	public void start() {
		GameElement.addListener(new OnceObjectCollisionListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionEnter(final GameObject object) {
				CollisionGameComponent.this.CollisionEnter(object);
			}
			
			@Override
			public void CollisionExit(final GameObject object) {
				CollisionGameComponent.this.CollisionExit(object);
			}
			
			@Override
			public void CollisionStay(final GameObject object) {
				CollisionGameComponent.this.CollisionStay(object);
			}
			
			@Override
			public GameObject getObject() {
				return this.getObject();
			}
		});
	}
}
