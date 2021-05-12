package com.greentree.engine.builder.xml.loaders;

import com.greentree.engine.builder.loaders.Loader;

/** @author Arseny Latyshev */
public interface SuperLoader {
	
	void addLoader(Loader function);
	
}
