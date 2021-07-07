package com.greentree.data.assets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.greentree.common.logger.Log;
import com.greentree.data.FileUtil;

public abstract class AssetUtil {

	private static final AssetHandlerList handlers = new AssetHandlerList();

	private static Asset create(File file) {
		try {
			return handlers.parse(file);
		}catch(Exception e) {
			Log.warn(e);
		}
		return null;
	}

	public static Collection<Asset> getAssets(File assets) throws IOException {
		if(assets.exists()) {
			if(!assets.isDirectory())throw new IllegalArgumentException("is not directory " + assets);
		}else throw new IllegalArgumentException("not exists " + assets);
		Collection<Asset> assets0 = new ArrayList<>();
		for(File file : FileUtil.getAllFile(assets)) {
			var res = AssetUtil.create(file);
			if(res != null)assets0.add(res);
		}
		return assets0;
	}

	public static void addAssetHandler(AssetHandler handler) {
		handlers.addParser(handler);
	}

}
