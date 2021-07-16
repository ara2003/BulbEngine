package com.greentree.engine.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.greentree.data.FileUtil;

public abstract class BuildUtil {

	public static Collection<String> getAllClass(File file){
		if(file.isFile()) {
			Collection<String> list = new ArrayList<>();
			if("java".equals(FileUtil.getType(file))) if(!"module-info.java".equals(file.getName()))list.add(getName(file));
			return list;
		}
		if(file.isDirectory()) {
			Collection<String> list = new HashSet<>();
			for(var f : file.listFiles()) list.addAll(getAllClass(f));
			return list;
		}
		return new ArrayList<>();
	}

	private static String getName(File file) {
		return new JavaFile(file).getName();
	}





}
