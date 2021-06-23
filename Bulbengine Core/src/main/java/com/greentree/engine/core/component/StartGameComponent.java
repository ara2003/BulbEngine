package com.greentree.engine.core.component;

import com.greentree.common.Starting;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.system.StartGameSystem;

@RequireSystems({StartGameSystem.class})
public abstract class StartGameComponent extends GameComponent implements Starting {

	@Override
	public abstract void start();

}
