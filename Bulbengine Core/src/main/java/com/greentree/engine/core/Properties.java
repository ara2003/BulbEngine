package com.greentree.engine.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public class Properties {
	
	private final static GameProperties PROPERTIES = new GameProperties();
	
	public static GameProperties getProperty() {
		return Properties.PROPERTIES;
	}
	
	public static String getProperty(final String key) {
		return Properties.PROPERTIES.getProperty(key);
	}
	
	public static void loadProperty(File file) {
		try {
			PROPERTIES.load(new FileInputStream(file));
		}catch(final IOException e) {
			throw new IllegalArgumentException(" file " + file + " load exception");
		}
	}

	public static String getOrDefault(String string, String string2) {
		return PROPERTIES.getOrDefault(string, string2);
	}

	public static void loadArguments(String[] args) {
		//TODO
	}
	
	public static final class GameProperties extends HashMap<String, String> implements Map<String, String> {
		private static final long serialVersionUID = 1L;

		public String getProperty(String key) {
			String res = get(key);
			if(res == null)return "";
			return res;
		}

		public String getProperty(String key, String def) {
			String res = getProperty(key);
			if(res.isEmpty())return def;
			return res;
		}

		public void load(InputStream inputStream) throws IOException {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
			for(String line : result.toString("UTF-8").split("\n")) {
				line = line.trim();
				String[] str = line.split("=");
				if(str.length != 2)throw new IOException("wrong format");
				put(str[0], str[1]);
			}
		}
		
		
		
	}
}
