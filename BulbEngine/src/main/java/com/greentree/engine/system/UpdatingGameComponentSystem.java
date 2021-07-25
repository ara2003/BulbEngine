package com.greentree.engine.system;

import com.greentree.common.Updating;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public class UpdatingGameComponentSystem extends MultiBehaviour {

	@Override
	public void update() {
		for(final Updating com : getAllComponents(Updating.class)) com.update();
	}

}
