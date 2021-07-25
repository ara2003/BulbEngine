package com.greentree.engine.component;

import com.greentree.common.Starting;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.system.StartGameComponentSystem;

@RequireSystems({StartGameComponentSystem.class})
public abstract class StartGameComponent extends GameComponent implements Starting {

	@Override
	public abstract void start();

}
