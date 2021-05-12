package com.greentree.engine.core.component;

import com.greentree.common.Updating;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.system.UpdatingComponentSystem;


/** @author Arseny Latyshev */
@RequireSystems(UpdatingComponentSystem.class)
public abstract class UpdatingGameComponent extends GameComponent implements Updating {
	
	@Override
	public abstract void update();
	
}
