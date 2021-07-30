package com.greentree.data.assets;

import java.io.InputStream;
import java.net.URL;

import com.greentree.data.loading.ResourceLocation;

public class AssetResourceLocation implements ResourceLocation {

	@Override
	public String toString() {
		return "AssetResourceLocation [context=" + context.getAssets() + "]";
	}

	private final AssetContext context;
	
	public AssetResourceLocation(AssetContext context) {
		super();
		this.context = context;
	}

	@Override
	public URL getResource(String name) {
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		try {
			return context.openWithName(name);
		}catch (IllegalArgumentException e) {
			return null;
		}
	}

}
