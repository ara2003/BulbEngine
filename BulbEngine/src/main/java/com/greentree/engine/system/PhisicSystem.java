package com.greentree.engine.system;

import org.joml.Vector2f;

import com.greentree.engine.Game;
import com.greentree.engine.collizion.CollisionListener;
import com.greentree.engine.component.PhisicComponent;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.util.Time;

/** @author Arseny Latyshev */
@NecessarilySystems({ColliderSystem.class})
@Deprecated
public class PhisicSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	
	public void collizion(final ColliderComponent c1, final ColliderComponent c2) {
		final PhisicComponent p1 = c1.getComponent(PhisicComponent.class);
		final PhisicComponent p2 = c2.getComponent(PhisicComponent.class);
		if(p1 == null) return;
		if(p2 == null) return;
		final Vector2f rv = p2.getVelosity().sub(p1.getVelosity());
		final Vector2f normal = c2.getShape().getCenter().getRadiusVector()
				.sub(c1.getShape().minPoint(c2.getShape().getCenter()).getRadiusVector()).normalize();
		float velAlongNormal = rv.dot(normal);
		if(velAlongNormal > 0) return;
		final float e = Math.min(p1.getElasticity(), p2.getElasticity());
		float j = -(1 + e) * velAlongNormal / (1 / p1.getMass() + 1 / p2.getMass());
		Vector2f impulse = normal.mul(j);
		p1.additionVelosity(impulse.mul(-1 / p1.getMass()));
		p2.additionVelosity(impulse.mul(1 / p2.getMass()));
	}
	
	@Override
	public void update() {
		for(PhisicComponent phisic : getAllComponentsAsComponentList(PhisicComponent.class)) {
			phisic.additionVelosity(new Vector2f(0, Time.getDelta()));
		}
	}
	
	@Override
	protected void start() {
		Game.addListener(new CollisionListener() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionEnter(ColliderComponent c1, ColliderComponent c2) {
				collizion(c1, c2);
			}
			
			@Override
			public void CollisionStay(final ColliderComponent c1, final ColliderComponent c2) {
				collizion(c1, c2);
			}

			@Override
			public void CollisionExit(ColliderComponent object1, ColliderComponent object2) {
			}
		});
	}
}
