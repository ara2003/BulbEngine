package com.greentree.engine.component;

import com.greentree.engine.GameComponent;
import com.greentree.engine.necessarily;
import com.greentree.engine.system.NetworkSystem;

/**
 * @author Arseny Latyshev
 */
@necessarily({NetworkSystem.class})
public class NetworkComponent extends GameComponent {
	private static final long serialVersionUID = 1L;

	@Override
	protected void start() {
		
	}

	@Override
	public void update() {
	
	}
	
}
