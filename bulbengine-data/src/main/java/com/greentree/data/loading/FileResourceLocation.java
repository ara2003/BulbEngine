package com.greentree.data.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.greentree.data.FileUtil;


public abstract class FileResourceLocation implements ResourceLocation {

	@Override
	public final URL getResource(String name) {
		try {
			File file = getFile(name);
			if(file == null)return null;
			return file.toURI().toURL();
		}catch(final IOException e) {
			return null;
		}
	}

	protected abstract File getFile(String name) throws IOException;

	@Override
	public final InputStream getResourceAsStream(String name) {
		try {
			File file = getFile(name);
			if(file == null)return null;
			return FileUtil.openStream(file);
		}catch(IOException e) {
		}
		return null;
	}

}
