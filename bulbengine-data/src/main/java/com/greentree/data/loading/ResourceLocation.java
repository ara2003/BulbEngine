package com.greentree.data.loading;

import java.io.InputStream;
import java.net.URL;

public interface ResourceLocation {
	
	URL getResource(final String name);
	InputStream getResourceAsStream(final String name);
}
