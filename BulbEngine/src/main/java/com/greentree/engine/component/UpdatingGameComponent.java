package com.greentree.engine.component;

import com.greentree.common.Updating;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.node.GameComponent;
import com.greentree.engine.system.UpdatingGameComponentSystem;


/** @author Arseny Latyshev */
@RequireSystems(UpdatingGameComponentSystem.class)
public abstract class UpdatingGameComponent extends GameComponent implements Updating {

	@Override
	public abstract void update();

}
