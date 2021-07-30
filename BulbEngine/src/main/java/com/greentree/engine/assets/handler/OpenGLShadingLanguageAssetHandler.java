package com.greentree.engine.assets.handler;

import java.io.File;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetHandler;


public class OpenGLShadingLanguageAssetHandler implements AssetHandler {

	@Override
	public boolean isLoadedFileType(String type) {
		return "glsl".equals(type);
	}

	@Override
	public Asset parse(File value) throws Exception {
		return new Asset("shader", value);
	}

}
