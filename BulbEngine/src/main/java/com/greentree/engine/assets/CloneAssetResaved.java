package com.greentree.engine.assets;

import java.io.InputStream;

import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetResaved;


public class CloneAssetResaved implements AssetResaved {

	@Override
	public boolean isSaveType(String type) {
		return true;
	}
	@Override
	public InputStream parse(Asset asset) throws Exception {
		return asset.getData();
	}

}
