package com.greentree.data.assets;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import com.greentree.common.function.SupplierWithException;
import com.greentree.data.FileUtil;

public final class Asset implements Serializable {

	@Override
	public String toString() {
		return "Asset [type=" + type + ", name=" + name + "]" + ((subType.length > 0)?Arrays.toString(subType):"");
	}

	private final String type, name;

	private final int[] subType;

	private final transient SupplierWithException<InputStream> newConnection;

	private static final long serialVersionUID = 1L;

	public Asset(String type, String name, SupplierWithException<InputStream> newConnection, int...subType) {
		this.type = type;
		this.name = name;
    	Arrays.sort(subType);
    	this.subType = subType;
		this.newConnection = newConnection;
	}
	public Asset(String type, File file, int...subType) {
		this(type, FileUtil.getName(file), () -> {
			return FileUtil.openStream(file);
		}, subType);
	}
	
	public InputStream getData(){
		try {
			return newConnection.get();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getName() {
		return name;
	}


	public int[] getSubType() {
		return subType;
	}

	public String getType() {
		return type;
	}

	public boolean hasSubType(int idSubType) {
		return Arrays.binarySearch(subType, idSubType) != -1;
	}



}
