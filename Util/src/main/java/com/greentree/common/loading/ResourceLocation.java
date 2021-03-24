package com.greentree.common.loading;

import java.io.InputStream;
import java.net.URL;

public interface ResourceLocation {
	
	URL getResource(final String name);
	InputStream getResourceAsStream(final String name);
}
