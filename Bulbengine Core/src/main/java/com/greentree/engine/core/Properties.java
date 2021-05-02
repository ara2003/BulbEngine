package com.greentree.engine.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.greentree.common.Optional;
import com.greentree.common.logger.Log;

/** @author Arseny Latyshev */
public class Properties {
	
	private final static GameProperties PROPERTIES = new GameProperties();
	
	public static String getOrDefault(final String string, final String string2) {
		return Properties.PROPERTIES.getOrDefault(string, string2);
	}
	
	public static GameProperties getProperties() {
		return Properties.PROPERTIES;
	}
	
	public static Optional<String> getProperty(final String key) {
		return Properties.PROPERTIES.getProperty(key);
	}
	
	public static void loadArguments(final String[] args) {
		//TODO
	}
	
	public static void loadProperty(final File file) {
		if(!file.exists()) try {
			file.createNewFile();
			Log.error("file " + file + " not exists");
		}catch(final IOException e) {
			Log.error(e);
		}
		try {
			Properties.PROPERTIES.load(new FileInputStream(file));
		}catch(final IOException e) {
			Log.error("file " + file + " load exception", e);
		}
	}
	
	public static final class GameProperties extends HashMap<String, String> implements Map<String, String> {
		
		private static final long serialVersionUID = 1L;
		
		public Optional<String> getProperty(final String key) {
			return new Optional<>(get(key));
		}
		
		public void load(final InputStream inputStream) throws IOException {
			final ByteArrayOutputStream result = new ByteArrayOutputStream();
			final byte[]                buffer = new byte[1024];
			int                         length;
			while((length = inputStream.read(buffer)) != -1) result.write(buffer, 0, length);
			for(String line : result.toString("UTF-8").split("\n")) {
				if(line.isEmpty()) continue;
				if(line.startsWith("#")) continue;
				line = line.trim();
				final String[] str = line.split("=");
				if(str.length < 2) throw new IOException("quantity \"=\" less then 1 in line :" + line);
				if(str.length > 2) throw new IOException("quantity \"=\" more then 1 in line :" + line);
				put(str[0], str[1]);
			}
		}
		
	
	}
	
}
