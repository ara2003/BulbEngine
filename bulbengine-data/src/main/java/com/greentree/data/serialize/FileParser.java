package com.greentree.data.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.greentree.data.loading.ResourceLoader;

/** TODO same thing
 * @author Arseny Latyshev */
@Deprecated
public class FileParser {
	
	private final Reader reader;
	private final Writer writer;
	
	public FileParser(final Reader reader, final Writer writer) {
		this.reader = reader;
		this.writer = writer;
	}
	
	private static <T> String getSuffix(final Class<T> clazz) {
		Objects.requireNonNull(clazz);
		final FileSufix s = clazz.getAnnotation(FileSufix.class);
		if(s == null) return clazz.getSimpleName().toLowerCase();
		else return s.value();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T load(final InputStream inputStream, final Class<T> clazz) throws IOException {
		String text = null;
		try(inputStream) {
			text = new String(inputStream.readAllBytes());
			inputStream.close();
		}catch(final IOException e) {
			throw e;
		}
		return (T) reader.read(text, clazz);
	}
	
	public <T> T load(final String name, final Class<T> clazz) throws IOException {
		return load(ResourceLoader.getResourceAsStream(name + "." + FileParser.getSuffix(clazz)), clazz);
		
	}
	
	public void save(final OutputStream outputStream, final Object obj) throws IOException {
		try(outputStream) {
			outputStream.write(writer.write(obj).getBytes());
			outputStream.close();
		}catch(final IOException e) {
			throw e;
		}
	}
	
	public void save(final String file, final Object obj) throws IOException {
		save(new FileOutputStream(new File(file + ".txt")), obj);
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
