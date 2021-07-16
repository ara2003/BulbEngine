package com.greentree.engine.system;

import com.greentree.engine.Game;
import com.greentree.engine.Windows;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.graphics.input.Key;


/**
 * @author Arseny Latyshev
 *
 */
public class ESCExtitSystem extends MultiBehaviour {

	@Override
	protected void start() {
		Windows.getWindow().getKeyPress().addListener(key -> {
			if(key == Key.ESCAPE.index())Game.exit();
		});
	}

}
