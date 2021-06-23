package com.greentree.engine.core.system;

import com.greentree.common.Updating;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public class UpdatingComponentSystem extends MultiBehaviour {

	@Override
	public void update() {
		for(final Updating com : getAllComponents(Updating.class)) com.update();
	}

}
