package com.greentree.engine.component;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.StartGameComponent;

@RequireComponent({ColliderComponent.class})
public class TestColliderLog extends StartGameComponent {
	
	@Override
	public void start() {
		getComponent(ColliderComponent.class).getAction().addStayListener(e -> {
			System.out.println(e.getObject().getName());
		});
	}
	
}
