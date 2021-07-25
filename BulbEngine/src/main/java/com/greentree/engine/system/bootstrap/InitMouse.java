package com.greentree.engine.system.bootstrap;

import com.greentree.engine.Mouse;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;


public class InitMouse extends MultiBehaviour {

	@Override
	protected void start() {
		Mouse.init();
	}

}
