package com.greentree.engine.component;

import com.greentree.engine.Mouse;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.util.Cameras;

@RequireComponent(Transform.class)
public class MousePurser extends UpdatingGameComponent {

	@Override
	public void update() {
		getComponent(Transform.class).position.set(Cameras.getMainCamera().getX() + Mouse.getMouseX() * 2, Cameras.getMainCamera().getY() + Mouse.getMouseY() * 2, 0);
	}

}
