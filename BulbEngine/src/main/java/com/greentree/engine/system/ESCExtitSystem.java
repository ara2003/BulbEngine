package com.greentree.engine.system;

import com.greentree.engine.Game3D;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.core.util.Events;
import com.greentree.graphics.input.Key;
import com.greentree.graphics.input.listener.KeyAdapter;


/**
 * @author Arseny Latyshev
 *
 */
public class ESCExtitSystem extends MultiBehaviour {

	@Override
	protected void start() {
		Events.addListener(new KeyAdapter() {

			@Override
			public void keyPress(int key) {
				if(key == Key.ESCAPE.index())Game3D.exit();
			}

		});
	}

}
