package com.greentree.data.loaders.collection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.greentree.common.xml.XMLElement;


/** @author Arseny Latyshev */
@SuppressWarnings("rawtypes")
public class ListLoader extends AbstactSubLoader<List> {

	public ListLoader() {
		super(List.class);
	}

	@Override
	public Object parse(Field field, XMLElement element) throws Exception {
		if(!"list".equals(element.getName()))throw new UnsupportedOperationException(element + " is not list");
		List<Object> list = new ArrayList<>();
		Class<?> g = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		for(var a : element.getChildrens()) {
			switch(a.getName()) {
				case "list_element" -> {
					list.add(subLoad(g, a.getAttribute("value")));
				}
			}
		}
		return list;
	}
}
