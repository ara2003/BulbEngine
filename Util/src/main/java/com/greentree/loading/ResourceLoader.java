package com.greentree.loading;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceLoader {
	
	private static final List<ResourceLocation> locations = new ArrayList<>();
	static {
		locations.add(new FileSystemLocation(new File(".")));
	}
	
	public static void addResourceLocation(final ResourceLocation location) {
		locations.add(Objects.requireNonNull(location));
	}
	
	public static URL getResource(final String ref) {
		URL url = null;
		for(final ResourceLocation location : locations) {
			url = location.getResource(ref);
			if(url != null) break;
		}
		if(url == null) throw new ResourceNotFound(ref + " in " + locations);
		return url;
	}
	
	public static InputStream getResourceAsStream(final String ref) {
		Objects.nonNull(ref);
		InputStream in = null;
		for(final ResourceLocation location : locations) {
			in = location.getResourceAsStream(ref);
			if(in != null) break;
		}
		if(in == null) throw new ResourceNotFound(ref + " in " + locations);
		return in;
	}
	
	public static boolean resourceExists(final String ref) {
		URL url = null;
		for(final ResourceLocation location : locations) {
			url = location.getResource(ref);
			if(url != null) return true;
		}
		return false;
	}
}
