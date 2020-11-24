package com.greentree.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class FileManager {
	
	public static File getDirectory(final File root, final String name) {
		final File file = new File(root, name);
		if(!Files.isExecutable(file.toPath())) try {
			Files.createDirectory(file.toPath());
		}catch(final IOException e) {
			Log.error(name, e);
		}
		return file;
	}
	
	public static File getDirectory(final String name) {
		return getDirectory(Game.getRoot(), name);
	}

	public static File getFile(final File root, final String name) {
		final File file = new File(root, name);
		if(!Files.isExecutable(file.toPath())) try {
			Files.createFile(file.toPath());
		}catch(final IOException e) {
			Log.error(name, e);
		}
		return file;
	}

	public static File getFile(final String name) {
		return getFile(Game.getRoot(), name);
	}

	private FileManager() {
	}
}
