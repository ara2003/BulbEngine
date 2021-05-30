package com.greentree.common;

import java.util.ArrayList;
import java.util.List;

/** @author Arseny Latyshev */
@Deprecated
public class PackageClassLoader extends ClassLoader {

	public PackageClassLoader() {
	}

	@Override
	public Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		return loadClass(name, resolve, new ArrayList<>());
	}

	public Class<?> loadClass(String className, final boolean resolve, List<String> packageNames) throws ClassNotFoundException {
		if(packageNames == null) throw new NullPointerException("packages is null");
		if(className == null) throw new NullPointerException("name is null");
		for(final String packageName : packageNames) try {
			Class<?> clazz = super.loadClass(packageName + className, resolve);
			if(clazz != null) return clazz;
		}catch(final ClassNotFoundException e) {
		}
		throw new ClassNotFoundException(className);
	}

	public final Class<?> loadClass(String className, List<String> packageNames) throws ClassNotFoundException {
		return loadClass(className, false, packageNames);
	}
}
