//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.greentree.engine.Game;

public class FileSystemLocation implements ResourceLocation {
	
	private final File root;
	
	public FileSystemLocation(final File root) {
		this.root = root;
	}
	
	@Override
	public URL getResource(String ref) {
		ref = Game.getRoot() + "\\" + ref;
		try {
			File file = new File(root, ref);
			if(!file.exists()) file = new File(ref);
			if(!file.exists()) return null;
			return file.toURI().toURL();
		}catch(final IOException e) {
			return null;
		}
	}
	
	@Override
	public InputStream getResourceAsStream(String ref) {
		ref = Game.getRoot() + "\\" + ref;
		try {
			File file = new File(root, ref);
			if(!file.exists()) file = new File(ref);
			return new FileInputStream(file);
		}catch(final IOException e) {
			return null;
		}
	}
}
