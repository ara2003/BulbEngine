package com.greentree.data.loading;

import java.io.File;
import java.nio.file.Path;

public class FileSystemLocation extends FileResourceLocation {
	
	private final File root;
	
	public FileSystemLocation(final File root) {
		if(root.isDirectory() == false) throw new IllegalArgumentException("root must by directory");
		this.root = root;
	}
	
	public FileSystemLocation(final Path path) {
		this(path.toFile());
	}
	
	public FileSystemLocation(final String file) {
		this(new File(file));
	}
	
	public FileSystemLocation() {
		root = null;
	}
	
	@Override
	public String toString() {
		return "FileSystemLocation" + ((root == null)?"":" [file=" + root + "]");
	}

	@Override
	public File getFile(String name) {
		if(root != null)
			return new File(root, name);
		return new File(name);
	}
}
