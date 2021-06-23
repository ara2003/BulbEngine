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
		if(RootFiles.started) throw new UnsupportedOperationException();
		RootFiles.started = true;
		RootFiles.root    = new File(file);
		if(!RootFiles.root.exists()) RootFiles.root.mkdir();
		RootFiles.assets = new File(RootFiles.root, "Assets");
		RootFiles.debug  = new File(RootFiles.root, "Debug");
		if(!RootFiles.assets.exists()) RootFiles.assets.mkdir();
		if(!RootFiles.debug.exists()) RootFiles.debug.mkdir();
		Log.init(RootFiles.debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(RootFiles.assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(RootFiles.root));
	}

}
