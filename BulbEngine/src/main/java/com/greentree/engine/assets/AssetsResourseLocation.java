package com.greentree.engine.assets;

import java.io.InputStream;
import java.net.URL;

import com.greentree.data.loading.ResourceLocation;

/**
 * @author Arseny Latyshev
 *
 */
public class AssetsResourseLocation implements ResourceLocation {	 

	private final AssetBundle assetBundle;
	
	public AssetsResourseLocation(AssetBundle assetBundle) {
		this.assetBundle = assetBundle;
	}
	
	@Override
	public URL getResource(String name) {
		return assetBundle.getResource(name);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		return assetBundle.openStream(name);
	}

}
