package com.greentree.data.loaders;

import java.lang.reflect.Field;

import com.greentree.common.xml.XMLElement;

/** @author Arseny Latyshev */
public interface Loader2 extends Loader {
	@Override
	default
	Object parse(Field field, XMLElement element, Object _default) throws Exception{
		return parse(field, element);
	}
	Object parse(Field field, XMLElement element) throws Exception;
}
