package com.greentree.engine;

import java.io.InputStream;

/**
 * @author Arseny Latyshev
 */
public interface Builder {
	
	GameNode createNode(final String prefab);
	GameNode createNode(final InputStream in);
	
	
}
