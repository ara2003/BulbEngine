package com.greentree.engine.render;

import com.greentree.engine.core.object.GameSystem;
import com.greentree.graphics.BulbGL;


public class InitBulbGL extends GameSystem {
	
	@Override
	protected void start() {
		BulbGL.init();
	}
	
}
