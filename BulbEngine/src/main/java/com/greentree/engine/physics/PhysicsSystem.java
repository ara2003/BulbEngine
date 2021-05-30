package com.greentree.engine.physics;

import java.util.stream.Collectors;

import org.joml.Vector2f;

import com.greentree.common.collection.DoubleSet;
import com.greentree.common.pair.Pair;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.collizion.event.CollisionListListener;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameSystem;

public class PhysicsSystem extends GameSystem {

	@Override
	protected void start() {
		Events.addListener(new CollisionListListener() {

			@Override
			public void ColliderList(final DoubleSet<ColliderComponent> list) {
				final var list0 = list.parallelStream()
						.filter(e->e.first.getObject().hasComponent(Physics2DComponent.class)
								&& e.second.getObject().hasComponent(Physics2DComponent.class))
						.collect(Collectors.toList());

				for(var p : list0) {
					GameObject a = p.first.getObject(), b = p.second.getObject();
					ColliderComponent ac = p.first, bc = p.second;
					Transform at = a.getComponent(Transform.class), bt = b.getComponent(Transform.class);
					Physics2DComponent ap = a.getComponent(Physics2DComponent.class), bp = b.getComponent(Physics2DComponent.class);

					Vector2f vec = at.xy().sub(bt.xy());
					if(vec.length() <= 1E-9)continue;
					vec.normalize(1 / 100f);

					//					System.out.println(vec + "  " + ap.getVelosity());

					float d = ac.getPenetrationDepth(bc) / 20f;
//					float d = 1;

					ap.getVelosity().add(vec.mul(d));
					bp.getVelosity().add(vec.mul(-d));
					System.out.println(d);
				}
				//				if(!list0.isEmpty())System.out.println();
			}
		});
	}

	@Override
	public void update() {
		for(Pair<Physics2DComponent, Transform> p : getAllComponents(Physics2DComponent.class).parallelStream()
				.map(e -> new Pair<>(e, e.getComponent(Transform.class)))
				.filter(p -> !p.second.isStatic())
				.collect(Collectors.toList())) {
			p.second.addXY(p.first.getVelosity());
			p.first.getVelosity().mul(0.99f);
		}
	}
}
