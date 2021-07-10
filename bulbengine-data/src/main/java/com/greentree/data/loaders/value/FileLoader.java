package com.greentree.data.loaders.value;

import java.io.File;

import com.greentree.data.FileUtil;
import com.greentree.data.loaders.AbstractLoader;


public class FileLoader extends AbstractLoader implements ValueLoader {

	public FileLoader() {
		super(File.class);
	}

	@Override
	public Object parse(Class<?> clazz, String value) throws Exception {
		return FileUtil.getFile(value);
	}

}
