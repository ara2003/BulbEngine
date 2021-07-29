package com.greentree.engine.system.bootstrap;

import com.greentree.engine.KeyBoard;
import com.greentree.engine.core.node.GameSystem.MultiBehaviour;


public class InitKeyBoard extends MultiBehaviour {

	@Override
	protected void start() {
		KeyBoard.init();
	}

}
