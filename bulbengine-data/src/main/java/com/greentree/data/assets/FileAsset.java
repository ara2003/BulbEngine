package com.greentree.data.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.greentree.common.logger.Log;


public class FileAsset implements Asset {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		FileAsset other = (FileAsset) obj;
		if(file == null) {
			if(other.file != null) return false;
		}else if(!file.equals(other.file)) return false;
		if(type == null) {
			if(other.type != null) return false;
		}else if(!type.equals(other.type)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileAsset [type=" + type + ", file=" + file + "]";
	}

	protected final String type;
	protected final File file;
	private final long id;
	
	public FileAsset(String type, File file) {
		if(!file.exists())throw new IllegalArgumentException("file does not exist");
		this.type = type;
		this.file = file;
		this.id = AssetUtil.nextID();
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public InputStream getData() {
		try {
			return new FileInputStream(file);
		}catch(FileNotFoundException e) {
			Log.error(e);//unreal
		}
		return null;
	}

	@Override
	public long getID() {
		return id;
	}

}
