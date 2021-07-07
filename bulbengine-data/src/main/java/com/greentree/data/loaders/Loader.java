package com.greentree.data.loaders;

import java.lang.reflect.Field;

import com.greentree.common.xml.XMLElement;
import com.greentree.data.parse.TripleParser;

/** @author Arseny Latyshev */
public interface Loader extends TripleParser<Field, XMLElement, Object, Object> {

	boolean isLoaded(Class<?> clazz);
	@Override
	Object parse(Field field, XMLElement element, Object _default) throws Exception;
}
