package com.greentree.engine.system.bootstrap;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;
import com.greentree.graphics.BulbGL;


public class InitBulbGL extends MultiBehaviour {

	@Override
	protected void start() {
		BulbGL.init();
	}

}
