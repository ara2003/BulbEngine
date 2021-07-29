package com.greentree.data.asset;

import java.io.InputStream;
import java.net.URL;

import com.greentree.data.loading.ResourceLocation;

public class AssetResourceLocation implements ResourceLocation {

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
		return context.openWithName(name);
	}

}
