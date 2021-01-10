package com.greentree.engine;

import java.io.InputStream;

/**
 * @author Arseny Latyshev
 */
public interface Builder {
	
	GameComponent createComponent(final InputStream data);
	GameObject createObject(final InputStream data);
	Scene createScene(final InputStream data);
	
}
