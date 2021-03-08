package com.greentree.engine;

import java.util.List;

/** @author Arseny Latyshev */
public abstract class GameClassLoader extends ClassLoader {
	
	public GameClassLoader() {
	}
	
	public GameClassLoader(final ClassLoader parent) {
	}
	
	@Override
	public Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		return loadClass(name, resolve, null);
	}
	
	public Class<?> loadClass(String name, final boolean resolve, List<String> packages) throws ClassNotFoundException {
		if(packages == null) packages = List.of("");
		Class<?> clazz = null;
		for(final String bin : packages) try {
			if(clazz != null) break;
			clazz = super.loadClass(bin + name, resolve);
		}catch(final ClassNotFoundException e) {
		}
		if(clazz == null) throw new ClassNotFoundException(name);
		return clazz;
	}
	
	public Class<?> loadClass(String name, List<String> packages) throws ClassNotFoundException {
		return loadClass(name, false, packages);
	}
}
