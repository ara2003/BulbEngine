package com.greentree.engine;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class Constants {
	
	protected static GameNode createNode(String file) {
		return Game.getMainNode().createNode(file);
	}
	
}
