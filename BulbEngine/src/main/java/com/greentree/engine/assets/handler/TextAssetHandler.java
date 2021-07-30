package com.greentree.engine.assets.handler;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;


public class TextAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		if("txt".equals(type))return true;
		return false;
	}

	@Override
	public Asset parse(File value) throws Exception {
		return new Asset("text", value);
	}

}
