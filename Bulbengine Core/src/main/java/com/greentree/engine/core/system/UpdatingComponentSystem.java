package com.greentree.engine.core.system;

import com.greentree.common.Updating;
import com.greentree.engine.core.object.GameSystem;

/**
 * @author Arseny Latyshev
 *
 */
@GroupSystem("updating")
public class UpdatingComponentSystem extends GameSystem {

	@Override
	protected void update() {
		for(Updating com : getAllComponents(Updating.class)) {
			com.update();
		}
	}
	
}
