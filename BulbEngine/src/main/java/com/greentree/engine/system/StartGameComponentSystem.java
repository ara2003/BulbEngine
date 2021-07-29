package com.greentree.engine.system;

import com.greentree.common.Starting;
import com.greentree.engine.core.node.GameSystem.MultiBehaviour;


public class StartGameComponentSystem extends MultiBehaviour {

	@Override
	protected void start() {
		addNewComponentListener(Starting.class, c -> {
			c.start();
		});
	}

	@Override
	public void update() {

	}

}
