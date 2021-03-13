package com.greentree.engine;

import java.util.Random;

import com.greentree.engine.event.Listener;

/** @author Arseny Latyshev */
public abstract class Constants {

	protected static final Random random = new Random();
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameObject createNode(String file) {
		return Game.getMainNode().createNode(file);
	}
}
