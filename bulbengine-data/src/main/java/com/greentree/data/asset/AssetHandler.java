package com.greentree.data.asset;

import java.io.File;

import com.greentree.data.parse.Parser;

public interface AssetHandler extends Parser<File, Asset> {
	
	
	boolean isLoadedFileType(String type);
	
}
