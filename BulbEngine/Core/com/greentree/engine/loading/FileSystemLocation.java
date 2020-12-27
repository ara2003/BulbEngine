package com.greentree.engine.loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileSystemLocation implements ResourceLocation {
	
	private final File root;
	
	public FileSystemLocation(final File root) {
		this.root = root;
	}
	
	@Override
	public URL getResource(String ref) {
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
		try {
			File file = new File(root, ref);
			if(!file.exists()) file = new File(ref);
			return new FileInputStream(file);
		}catch(final IOException e) {
		}
		return null;
	}
}
