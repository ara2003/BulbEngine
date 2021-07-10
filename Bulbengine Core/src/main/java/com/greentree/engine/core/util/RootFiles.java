package com.greentree.engine.core.util;


import java.io.File;

import com.greentree.common.logger.Log;
import com.greentree.data.loading.FileSystemLocation;
import com.greentree.data.loading.ResourceLoader;

/** @author Arseny Latyshev */
public class RootFiles {

	private static File root, assets, debug;
	private static boolean started;

	public static File getAssets() {
		return RootFiles.assets;
	}

	public static File getRoot() {
		return RootFiles.root;
	}

	public static void start(final String file) {
		if(started) throw new UnsupportedOperationException();
		started = true;
		root    = new File(file);
		if(!root.exists()) root.mkdir();
		assets = new File(root, "Assets");
		debug  = new File(root, "Debug");
		if(!assets.exists()) assets.mkdir();
		if(!debug.exists()) debug.mkdir();
		{
			var build = new File(root, "Build");
			if(!build.exists()) build.mkdir();
		}
		Log.init(debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(RootFiles.assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(RootFiles.root));
	}

}
