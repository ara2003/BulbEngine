package com.greentree.engine.core;

import java.io.File;

import com.greentree.common.Log;
import com.greentree.common.loading.FileSystemLocation;
import com.greentree.common.loading.ResourceLoader;

/**
 * @author Arseny Latyshev
 *
 */
public class RootFiles {
	
	private static File root, assets, debug;
	private static boolean started;
	
	public static File getRoot() {
		return RootFiles.root;
	}
	public static File getAssets() {
		return RootFiles.assets;
	}
	
	public static void start(String file) {
		if(started)throw new UnsupportedOperationException();
		started = true;
		RootFiles.root   = new File(file);
		RootFiles.assets = new File(root, "Assets");
		RootFiles.debug  = new File(root, "Debug");
		Log.setLogFolder(debug);
		ResourceLoader.addResourceLocation(new FileSystemLocation(assets));
		ResourceLoader.addResourceLocation(new FileSystemLocation(root));
	}
	
}
