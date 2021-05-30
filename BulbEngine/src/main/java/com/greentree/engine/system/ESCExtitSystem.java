package com.greentree.engine.system;

import com.greentree.engine.Game3D;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.graphics.input.Key;
import com.greentree.graphics.input.listener.KeyAdapter;


/**
 * @author Arseny Latyshev
 *
 */
public class ESCExtitSystem extends GameSystem {
	
	@Override
	protected void start() {
		addListener(new KeyAdapter() {
			
			@Override
			public void keyPress(int key) {
				if(key == Key.ESCAPE.index())Game3D.exit();
			}
			
		});
	}
	
}
