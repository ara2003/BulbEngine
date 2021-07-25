package com.greentree.engine.assets;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;
import com.greentree.data.assets.FileAsset;


public class JPGImageAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return "jpg".equals(type);
	}

	@Override
	public Asset parse(File value) throws Exception {
		return new FileAsset("image", value);
	}

}
