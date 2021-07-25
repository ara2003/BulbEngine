package com.greentree.engine2d.collizion;

import com.greentree.engine.component.StartGameComponent;
import com.greentree.engine.core.builder.RequireComponent;

@RequireComponent({ColliderComponent.class})
public class TestColliderLog extends StartGameComponent {

	@Override
	public void start() {
		getComponent(ColliderComponent.class).getAction().addStayListener(e -> {
			System.out.println(e.getObject().getName());
		});
	}

}
