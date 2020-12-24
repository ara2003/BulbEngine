package com.greentree.serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.greentree.engine.Log;
import com.greentree.engine.loading.ResourceLoader;

public class FileParser {
	
	private final Reader reader;
	private final Writer writer;
	
	public FileParser(Reader reader, Writer writer) {
		this.reader = reader;
		this.writer = writer;
	}
	
	private static <T> String getSuffix(final Class<T> clazz) {
		Objects.requireNonNull(clazz);
		fileSufix s = clazz.getAnnotation(fileSufix.class);
		if(s == null) {
			return clazz.getSimpleName().toLowerCase();
		}else {
			return s.value();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T load(final InputStream inputStream, final Class<T> clazz) {
		String text = null;
		try(inputStream) {
			text = new String(inputStream.readAllBytes());
			inputStream.close();
		}catch(final IOException e) {
			Log.error(e);
		}
		if(text == null) return null;
		return (T) reader.read(text, clazz);
	}
	
	public <T> T load(final String name, final Class<T> clazz) {
		return load(ResourceLoader.getResourceAsStream(name + "." + getSuffix(clazz)), clazz);
		
	}
	
	public void save(final OutputStream outputStream, final Object obj) {
		try(outputStream) {
			outputStream.write(writer.write(obj).getBytes());
			outputStream.close();
		}catch(final IOException e) {
			Log.error(e);
		}
	}
	
	public void save(String file, Object obj) {
		try {
			save(new FileOutputStream(new File(file+".txt")), obj);
		}catch(FileNotFoundException e) {
			Log.warn(e);
		}
	}
	
	@FunctionalInterface
	public interface Reader {
		
		Object read(String text, Class<?> clazz);
		
	}
	
	@FunctionalInterface
	public interface Writer {
		
		String write(Object obj);
		
	}
	
}
