package com.greentree.engine.builder.xml.loaders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.greentree.common.ClassUtil;
import com.greentree.common.xml.XMLElement;
import com.greentree.engine.builder.loaders.Loader;


/** @author Arseny Latyshev */
public class ListLoader extends AbstractSuperLoader<List> {
	
	public ListLoader() {
		super(List.class);
	}
	
	@Override
	public Object load(final Class<?> clazz, final XMLElement value) throws Exception {
		if(!"list".equals(value.getName())) throw new UnsupportedOperationException("it\'s not List");
		final List<Object> list = new ArrayList<>();
		
		System.out.println("f " + ClassUtil.getAllFields(ArrayList.class).parallelStream().collect(Collectors.toList()));
		
		for(final var a : value.getChildrens("element")) list.add(subLoad(a.getAttribute("value")));
		return list;
	}
	
	@Override
	public Object load(final XMLElement value) throws Exception {
		throw new UnsupportedOperationException("use Object com.greentree.engine.builder.xml.ListLoader.load(Class<?> clazz, XMLElement value) throws Exception");
	}
	
	@Override
	public void addLoader(Loader function) {
	}
	
	@Override
	public boolean isLoaded(Field field) {
		return false;
	}
	
}
