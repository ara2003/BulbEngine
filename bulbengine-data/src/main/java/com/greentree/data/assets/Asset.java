package com.greentree.data.assets;

import java.io.InputStream;

public interface Asset {
	
	String getType();
	InputStream getData();
	
}
