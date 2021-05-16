package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class BooleanLoader extends PrimitiveLoader<Boolean> {
	
	public BooleanLoader() {
		super(boolean.class);
	}
	
	@Override
	public Boolean parse(final String value) {
		if("0".equals(value))return false;
		if("1".equals(value))return true;
		return Boolean.parseBoolean(value);
	}
}
