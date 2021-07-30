package com.greentree.engine;


import java.io.File;

import com.greentree.common.logger.Log;
import com.greentree.data.assets.AssetResourceLocation;
import com.greentree.data.assets.AssetUtil;
import com.greentree.data.loading.FileSystemLocation;
import com.greentree.data.loading.ResourceLoader;

/** @author Arseny Latyshev */
public class RootFiles {

	private static File root;
	private static boolean started;

	public static File getAssets() {
		return new File(root, "Build\\data\\Assets");
	}
	
	public static File getOriginalAssets() {
		return new File(root, "Build\\Assets");
	}

	public static void start(File root) {
		RootFiles.root = root;
		if(started) throw new UnsupportedOperationException();
		started = true;
		File assets = new File(root, "data\\Assets");
		File debug  = new File(root, "Debug");
		if(!assets.exists()) assets.mkdir();
		if(!debug.exists()) debug.mkdir();
		Log.init(debug);
		
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(root, "data")));
		
		ResourceLoader.addResourceLocation(new AssetResourceLocation(AssetUtil.getContext(assets)));
	}

}
