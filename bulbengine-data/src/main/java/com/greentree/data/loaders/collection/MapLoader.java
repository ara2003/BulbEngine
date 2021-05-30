package com.greentree.data.loaders.collection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import com.greentree.common.xml.XMLElement;


/** @author Arseny Latyshev */
@SuppressWarnings("rawtypes")
public class MapLoader extends AbstactSubLoader<Map> {

	public MapLoader() {
		super(Map.class);
	}

	@Override
	public Object parse(Field field, XMLElement element) throws Exception {
		if(!"map".equals(element.getName()))throw new UnsupportedOperationException(element + " is not map");
		Map<Object, Object> list = new HashMap<>();
		Class<?> k = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		Class<?> v = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];
		for(var a : element.getChildrens()) {
			switch(a.getName()) {
				case "map_element" -> {
					var K = subLoad(k, a.getAttribute("key"));
					var V = subLoad(v, a.getAttribute("value"));
					list.put(K, V);
				}
			}
		}
		return list;
	}
}
