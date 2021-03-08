package com.greentree.engine.loading;

import java.io.InputStream;
import java.net.URL;

public interface ResourceLocation {
	
	URL getResource(final String p0);
	InputStream getResourceAsStream(final String p0);
}
