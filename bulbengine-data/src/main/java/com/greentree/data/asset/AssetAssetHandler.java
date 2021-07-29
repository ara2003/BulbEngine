package com.greentree.data.asset;

import java.io.File;


public class AssetAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return AssetUtil.ASSET_FILE_EXTENSION.equals(type);
	}

	@Override
	public Asset parse(File file) throws Exception {
		return AssetUtil.load(file);
	}

}
