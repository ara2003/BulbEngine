package com.greentree.data.loaders;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.greentree.common.xml.XMLElement;


public class ConstructorLoader extends SubLoader implements Loader2 {

	@Override
	public boolean isLoaded(Class<?> clazz) {
		if(clazz.isPrimitive() || clazz.isArray() || clazz.isEnum() || clazz.isInterface())return false;
		return true;
	}

	@Override
	public Object parse(Field field, XMLElement element) throws Exception {
		List<String> list = element.getChildrens("parameter").parallelStream().map(xmle -> xmle.getContent()).collect(Collectors.toList());
		for(Constructor<?> c : field.getType().getConstructors()) {
			if(list.size() != c.getParameterCount())continue;
			Class<?>[] classes = c.getParameterTypes();
			List<Object> list0 = new ArrayList<>();
			for(int i = 0; i < list.size(); i++) try{
				list0.add(subLoad(classes[i], list.get(i)));
			}catch (Exception e) {
			}
			if(list0.size() != c.getParameterCount())continue;
			return c.newInstance(list0.toArray());
		}
		throw new RuntimeException();
	}

}
