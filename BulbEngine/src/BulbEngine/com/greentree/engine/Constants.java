package com.greentree.engine;

import com.greentree.engine.event.Listener;

/** @author Arseny Latyshev */
public abstract class Constants {
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameNode createNode(String file) {
		return Game.getMainNode().createNode(file);
	}
}
