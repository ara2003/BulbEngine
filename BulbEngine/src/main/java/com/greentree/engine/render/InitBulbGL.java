package com.greentree.engine.render;

import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.graphics.BulbGL;


public class InitBulbGL extends MultiBehaviour {

	@Override
	protected void start() {
		BulbGL.init();
	}

}
