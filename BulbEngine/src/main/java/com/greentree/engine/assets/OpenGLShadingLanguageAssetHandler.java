package com.greentree.engine.assets;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;
import com.greentree.data.assets.FileAsset;


public class OpenGLShadingLanguageAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return "glsl".equals(type);
	}

	@Override
	public Asset parse(File value) throws Exception {
		return new FileAsset("shader", value);
	}

}
