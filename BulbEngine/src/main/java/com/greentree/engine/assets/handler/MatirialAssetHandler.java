package com.greentree.engine.assets.handler;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;


public class MatirialAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return "matirial".equals(type);
	}

	@Override
	public Asset parse(File value) throws Exception {
		return new Asset("matirial", value);
	}

}
