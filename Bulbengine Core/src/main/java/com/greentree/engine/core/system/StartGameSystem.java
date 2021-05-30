package com.greentree.engine.core.system;

import com.greentree.common.Starting;
import com.greentree.engine.core.object.GameSystem;


public class StartGameSystem extends GameSystem {
	
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
