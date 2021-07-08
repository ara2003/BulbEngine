package com.greentree.data.assets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.greentree.common.logger.Log;
import com.greentree.data.FileUtil;

public abstract class AssetUtil {

	private static final AssetHandlerList handlers = new AssetHandlerList();

	public static void addAssetHandler(AssetHandler handler) {
		handlers.addParser(handler);
	}

	private static Asset create(File file) {
		try {
			return handlers.parse(file);
		}catch(Exception e) {
			Log.warn(e);
		}
		return null;
	}

	public static Asset getAsset(File file) {
		if(file.exists()) {
			if(!file.isFile())throw new IllegalArgumentException("is not file " + file);
			return create(file);
		}else throw new IllegalArgumentException("not exists " + file);
	}

	public static Collection<Asset> getAssets(File assets) throws IOException {
		if(assets.exists()) {
			if(!assets.isDirectory())throw new IllegalArgumentException("is not directory " + assets);
    		Collection<Asset> assets0 = new ArrayList<>();
    		for(File file : FileUtil.getAllFile(assets)) {
    			var res = AssetUtil.create(file);
    			if(res != null)assets0.add(res);
    		}
    		return assets0;
		}
		throw new IllegalArgumentException("not exists " + assets);
	}

}
