package com.greentree.engine.editor.xml;


/**
 * @author Arseny Latyshev
 *
 */

public interface Loader {
	
	Object load(Class<?> fieldClass, String value) throws Exception;
	
}