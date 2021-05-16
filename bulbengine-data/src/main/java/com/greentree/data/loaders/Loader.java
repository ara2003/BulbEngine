package com.greentree.data.loaders;

import java.lang.reflect.Field;

import com.greentree.common.xml.XMLElement;
import com.greentree.data.parse.PairParser;

/** @author Arseny Latyshev */
public interface Loader extends PairParser<Field, XMLElement, Object> {
	
	boolean isLoaded(Class<?> clazz);
	@Override
	Object parse(Field field, XMLElement element) throws Exception;
}
