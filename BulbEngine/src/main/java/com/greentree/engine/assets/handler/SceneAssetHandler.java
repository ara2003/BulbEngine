package com.greentree.engine.assets.handler;

import java.io.File;

import com.greentree.common.xml.XMLElement;
import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;


public class SceneAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return "xml".equals(type);
	}

	@Override
	public Asset parse(File value) throws Exception {
		XMLElement e = new XMLElement(value);
		if("scene".equals(e.getName())) return new Asset("scene", value);
		throw new IllegalArgumentException("is not scene");
	}

}
