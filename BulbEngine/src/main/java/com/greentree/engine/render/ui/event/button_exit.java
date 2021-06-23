package com.greentree.engine.render.ui.event;

import com.greentree.engine.Game3D;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.engine.render.ui.Button;

public class button_exit extends StartGameComponent {

	@Override
	public void start() {
		this.getComponent(Button.class).getAction().addListener(mouseButton->Game3D.exit());
	}
}
