package com.greentree.data.assets;

import java.io.InputStream;

public interface Asset {
	
	long getID();
	String getType();
	InputStream getData();
	
}
