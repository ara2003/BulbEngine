package com.greentree.engine.core.system;

import com.greentree.common.Updating;
import com.greentree.engine.core.GameSystem;

/**
 * @author Arseny Latyshev
 *
 */
public class UpdatingComponentSystem extends GameSystem {

	@Override
	protected void update() {
		for(Updating com : getAllComponents(Updating.class)) {
			com.update();
		}
	}
	
}
