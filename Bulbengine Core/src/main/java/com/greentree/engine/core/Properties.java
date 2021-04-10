package com.greentree.engine.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.greentree.common.logger.Log;

/** @author Arseny Latyshev */
public class Properties {
	
	private final static GameProperties PROPERTIES = new GameProperties();
	
	public static String getOrDefault(final String string, final String string2) {
		return Properties.PROPERTIES.getOrDefault(string, string2);
	}
	
	public static GameProperties getProperty() {
		return Properties.PROPERTIES;
	}
	
	public static String getProperty(final String key) {
		return Properties.PROPERTIES.getProperty(key);
	}
	
	public static String getPropertyNotNull(final String key) {
		return Properties.PROPERTIES.getPropertyNotNull(key);
	}
	
	public static void loadArguments(final String[] args) {
		//TODO
	}
	
	public static void loadProperty(final File file) {
		if(!file.exists()) try {
			file.createNewFile();
		}catch(final IOException e) {
			Log.error(e);
		}
		try {
			Properties.PROPERTIES.load(new FileInputStream(file));
		}catch(final IOException e) {
			throw new IllegalArgumentException(" file " + file + " load exception", e);
		}
	}
	
	public static final class GameProperties extends HashMap<String, String> implements Map<String, String> {
		
		private static final long serialVersionUID = 1L;
		
		public String getProperty(final String key) {
			return getProperty(key, "");
		}
		
		public String getProperty(final String key, final String def) {
			final String res = this.get(key);
			if(res == null) return def;
			return res;
		}
		
		public String getPropertyNotNull(final String key) {
			String res = get(key);
			if(res == null)throw new NullPointerException(String.format("argument %s does not exist", key));
			return res;
		}
		
		public void load(final InputStream inputStream) throws IOException {
			final ByteArrayOutputStream result = new ByteArrayOutputStream();
			final byte[]                buffer = new byte[1024];
			int                         length;
			while((length = inputStream.read(buffer)) != -1) result.write(buffer, 0, length);
			for(String line : result.toString("UTF-8").split("\n")) {
				if(line.isEmpty()) continue;
				line = line.trim();
				final String[] str = line.split("=");
				if(str.length < 2) throw new IOException("quantity \"=\" less then 1 in line :" + line);
				if(str.length > 2) throw new IOException("quantity \"=\" more then 1 in line :" + line);
				this.put(str[0], str[1]);
			}
		}
		
		
		
	}
}
