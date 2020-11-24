package com.greentree.engine.system;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.component.CircleColliderComponent;
import com.greentree.engine.component.ColliderComponent;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.system.util.ISystem;

public class ColliderSystem implements ISystem {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(final List<GameComponent> list) {
		final List<ColliderComponent> s = new ArrayList<>();
		list.forEach(a-> {
			if(a instanceof ColliderComponent) s.add((CircleColliderComponent) a);
		});
		execute0(s);
	}

	private void execute0(final List<ColliderComponent> gs) {
		for(final ColliderComponent a : gs) for(final ColliderComponent b : gs) {
			if(a == b) break;
			if(!a.getShape().contact(b.getShape()).isEmpty()) {
				a.getObject().CollideEvent(b.getObject());
				b.getObject().CollideEvent(a.getObject());
			}
		}
	}
}
