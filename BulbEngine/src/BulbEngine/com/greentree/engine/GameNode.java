package com.greentree.engine;

import java.io.Serializable;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class GameNode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected final String name;
	
	public GameNode(String name) {
		this.name = name;
	}
	
}
