package com.greentree.data.loaders.value;

import java.lang.reflect.Field;

import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.Loader2;

/** @author Arseny Latyshev */
public interface ValueLoader extends Loader2 {

	@Override
		default Object parse(Field value1, XMLElement value2) throws Exception {
			return parse(value1.getType(), value2.getContent());
		}
	
	Object parse(Class<?> clazz, String value) throws Exception;
	
}
