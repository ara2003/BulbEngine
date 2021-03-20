package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

public interface OnceCollisionListener extends SimpleCollisionListener, ObjectCollisionListener {
	
	void CollisionEnter(final ColliderComponent object);
	
	@Override
	default void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2) {
		if(this.getCollider().equals(object1)) this.CollisionEnter(object2);
		else if(this.getCollider().equals(object2)) this.CollisionEnter(object1);
	}
	
	void CollisionExit(final ColliderComponent object);
	
	@Override
	default void CollisionExit(final ColliderComponent object1, final ColliderComponent object2) {
		if(this.getCollider().equals(object1)) this.CollisionEnter(object2);
		else if(this.getCollider().equals(object2)) this.CollisionEnter(object1);
	}
	
	void CollisionStay(final ColliderComponent object);
	
	@Override
	default void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
		if(this.getCollider().equals(object1)) this.CollisionEnter(object2);
		else if(this.getCollider().equals(object2)) this.CollisionEnter(object1);
	}
	
	default ColliderComponent getCollider() {
		return this.getObject().getComponent(ColliderComponent.class);
	}
	
	GameObject getObject();
}
