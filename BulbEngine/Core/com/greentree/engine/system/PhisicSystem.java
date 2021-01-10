package com.greentree.engine.system;

import com.greentree.engine.Game;
import com.greentree.engine.Time;
import com.greentree.engine.necessarily;
import com.greentree.engine.component.Phisic;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.phisic.ColliderListener;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.math.vector.float2f;

/**
 *  @author Arseny Latyshev
 */
@necessarily({ColliderSystem.class})
@Deprecated
public class PhisicSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	
	public void collizion(final ColliderComponent c1, final ColliderComponent c2) {
		final Phisic p1 = c1.getComponent(Phisic.class);
		final Phisic p2 = c2.getComponent(Phisic.class);
		if(p1 == null) return;
		if(p2 == null) return;
		
		final float2f rv = p2.getVelosity().addition(p1.getVelosity().multiply(-1));
		
		final float2f normal = c2.getShape().getCenter().getRadiusVector().addition(c1.getShape().minPoint(c2.getShape().getCenter()).getRadiusVector().multiply(-1)).scaleTo(1);
		
		
		float velAlongNormal = (float) rv.scalarMultiply(normal);
		
		if(velAlongNormal > 0)
			return;
		
		final float e = Math.min(p1.getElasticity(), p2.getElasticity());
		
		float j = ((-(1 + e) * velAlongNormal) / ((1 / p1.getMass()) + (1 / p2.getMass())));
		
		float2f impulse = normal.multiply(j);
		
		p1.additionVelosity(impulse.multiply(-1 / p1.getMass()));
		p2.additionVelosity(impulse.multiply( 1 / p2.getMass()));
	}
	
	@Override
	public void execute() {
		for(Phisic phisic : getComponents(Phisic.class)) {
			phisic.additionVelosity(new float2f(0, 0.001f * Time.getDelta()));
		}
	}
	
	@Override
	protected void start() {
		Game.addListener(new ColliderListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void CollisionEnter(ColliderComponent c1, ColliderComponent c2) {
				collizion(c1, c2);
			}
			
			@Override
			public void CollisionStay(final ColliderComponent c1, final ColliderComponent c2) {
				collizion(c1, c2);
			}
			
		});
	}
}
