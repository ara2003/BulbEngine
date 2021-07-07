package com.greentree.engine.assets;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;
import com.greentree.data.assets.FileAsset;


public class MatirialAssetHandler implements AssetHandler {

	@Override
	public Asset parse(File value) throws Exception {
		return new FileAsset("matirial", value);
	}

	@Override
	public boolean isLoadedFileType(String type) {
		return "matirial".equals(type);
	}
	
}
