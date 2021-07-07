package com.greentree.engine.assets;

import java.io.File;

import com.greentree.common.xml.XMLElement;
import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;
import com.greentree.data.assets.FileAsset;


public class PrefabAssetHandler implements AssetHandler {

	@Override
	public Asset parse(File value) throws Exception {
		XMLElement e = new XMLElement(value);
		if("object".equals(e.getName())) {
			return new FileAsset("prefab", value);
		}
		throw new IllegalArgumentException("is not prefab");
	}

	@Override
	public boolean isLoadedFileType(String type) {
		return "xml".equals(type);
	}
}
