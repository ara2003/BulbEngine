package com.greentree.data.loaders.collection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.greentree.common.xml.XMLElement;


/** @author Arseny Latyshev */
@SuppressWarnings("rawtypes")
public class TableLoader extends AbstactSubLoader<Table> {

	public TableLoader() {
		super(Table.class);
	}

	@Override
	public Object parse(Field field, XMLElement element) throws Exception {
		Table<Object, Object, Object> list = HashBasedTable.create();
		Class<?> r = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		Class<?> c = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];
		Class<?> v = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[2];
		for(var a : element.getChildrens("cell")) {
			list.put(subLoad(r, a.getAttribute("row")), subLoad(c, a.getAttribute("column")), subLoad(v, a.getAttribute("value")));
		}
		return list;
	}
}
