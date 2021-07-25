package com.greentree.data.assets;

import java.io.File;

import com.greentree.data.parse.Parser;

public interface AssetHandler extends Parser<File, Asset> {
	
	public abstract boolean isLoadedFileType(String type);
	
}
