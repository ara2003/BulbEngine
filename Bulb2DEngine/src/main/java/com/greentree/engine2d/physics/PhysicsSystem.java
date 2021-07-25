package com.greentree.engine2d.physics;

import java.util.stream.Collectors;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.AbstractVector3f;
import com.greentree.common.pair.Pair;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine2d.collizion.ColliderComponent;
import com.greentree.engine2d.collizion.ColliderSystem;

public class PhysicsSystem extends MultiBehaviour {

	@Override
	protected void start() {
		getScene().getSystem(ColliderSystem.class).getBehaviour().getAction().addListener(list-> {
			final var list0 = list.parallelStream()
					.filter(e->e.first.getObject().hasComponent(Physics2DComponent.class)
							&& e.seconde.getObject().hasComponent(Physics2DComponent.class))
					.collect(Collectors.toList());

			for(var p : list0) {
				GameObject a = p.first.getObject(), b = p.seconde.getObject();
				ColliderComponent ac = p.first, bc = p.seconde;
				AbstractVector3f at = a.getComponent(Transform.class).position, bt = b.getComponent(Transform.class).position;
				Physics2DComponent ap = a.getComponent(Physics2DComponent.class), bp = b.getComponent(Physics2DComponent.class);

				AbstractVector2f vec = at.xy().sub(bt.xy());
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
		});
	}

	@Override
	public void update() {
		for(Pair<Physics2DComponent, Transform> p : getAllComponents(Physics2DComponent.class).parallelStream()
				.map(e -> new Pair<>(e, e.getComponent(Transform.class)))
				.filter(p -> !p.seconde.isStatic())
				.collect(Collectors.toList())) {
			p.seconde.position.addXY(p.first.getVelosity());
			p.first.getVelosity().mul(0.99f);
		}
	}
}
