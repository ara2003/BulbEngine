package com.greentree.engine;

import java.io.Serializable;

public interface gsonSerializable extends Serializable {
	
	long serialVersionUID = 1L;
	
	default String extension() {
		return "*";
	}
}
