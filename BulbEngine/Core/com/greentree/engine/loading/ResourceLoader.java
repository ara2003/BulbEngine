//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.loading;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ResourceLoader {
	
	private static ArrayList<ResourceLocation> locations;
	static {
		ResourceLoader.locations = new ArrayList<>();
		ResourceLoader.locations.add(new FileSystemLocation(new File(".")));
	}

	private ResourceLoader() {
	}

	public static void addResourceLocation(final ResourceLocation location) {
		ResourceLoader.locations.add(location);
	}
	
	public static URL getResource(final String ref) {
		URL url = null;
		for(final Object element : ResourceLoader.locations) {
			final ResourceLocation location = (ResourceLocation) element;
			url = location.getResource(ref);
			if(url != null) break;
		}
		if(url == null) throw new RuntimeException("Resource not found: " + ref);
		return url;
	}
	
	public static InputStream getResourceAsStream(final String ref) {
		InputStream in = null;
		for(final ResourceLocation location : ResourceLoader.locations) {
			in = location.getResourceAsStream(ref);
			if(in != null) break;
		}
		if(in == null) throw new RuntimeException("Resource not found: " + ref);
		return new BufferedInputStream(in);
	}
	
	public static void removeAllResourceLocations() {
		ResourceLoader.locations.clear();
	}
	
	public static void removeResourceLocation(final ResourceLocation location) {
		ResourceLoader.locations.remove(location);
	}
	
	public static boolean resourceExists(final String ref) {
		URL url = null;
		for(final Object element : ResourceLoader.locations) {
			final ResourceLocation location = (ResourceLocation) element;
			url = location.getResource(ref);
			if(url != null) return true;
		}
		return false;
	}
}
