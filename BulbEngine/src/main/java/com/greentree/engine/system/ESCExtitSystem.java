package com.greentree.engine.system;

import com.greentree.engine.Game;
import com.greentree.engine.core.node.GameSystem.MultiBehaviour;
import com.greentree.engine.util.Windows;
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
