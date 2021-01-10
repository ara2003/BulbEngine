package com.greentree.engine;

public class BasicClassLoader extends ClassLoader {
	
	private final String[] packages;
	
	public BasicClassLoader(final ClassLoader parent, final String[] packages) {
		super(parent);
		this.packages = new String[packages.length + 1];
		this.packages[0] = "";
		for(int i = 0; i < packages.length; i++) this.packages[i + 1] = packages[i] + ".";
	}
	
	public BasicClassLoader(final String[] packages) {
		this(getSystemClassLoader(), packages);
	}
	
	@Override
	public Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = null;
		for(final String bin : packages) try {
			if(clazz != null) break;
			
			clazz = super.loadClass(bin + name, resolve);
		}catch(final ClassNotFoundException e) {
		}
		return clazz;
	}
}
