package com.greentree.engine.core.util;


import java.io.File;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.FileSystemLocation;
import com.greentree.data.loading.ResourceLoader;

/** @author Arseny Latyshev */
public class RootFiles {

	private static File assets, debug;
	private static boolean started;

	public static File getAssets() {
		return RootFiles.assets;
	}

	public static void start(File root) {
		if(started) throw new UnsupportedOperationException();
		started = true;
		assets = new File(root, "Assets");
		debug  = new File(root, "Debug");
		if(!assets.exists()) assets.mkdir();
		if(!debug.exists()) debug.mkdir();
		Log.init(debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(RootFiles.assets));
	}

}
