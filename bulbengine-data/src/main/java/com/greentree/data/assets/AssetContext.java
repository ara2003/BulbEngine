package com.greentree.data.assets;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.greentree.data.InputStreamUtil;

public final class AssetContext {

	public class Hasher {

		private int magic;

		protected Hasher(int magic) {
			this.magic = magic;
		}

		public int getHash(String name) {
			return InputStreamUtil.readString(getWithName(name).getData()).hashCode() ^ magic;
		}
		
	}

	public void saveTo(File toDirectory) {
		
		for(var asset : getAssets()) {
			AssetUtil.save(toDirectory, asset);
		}
	}


	public Hasher getHasher(int magic){
		return new Hasher(magic);
	}
	protected final Map<String, Asset> names = new HashMap<>();
	protected final Map<String, Collection<Asset>> types = new HashMap<>();

	public AssetContext() {
	}

	public AssetContext(Collection<Asset> assets) {
		for(var a : assets) add(a);
	}

	protected final void add(Asset asset) {
		var type = types.get(asset.getType());
		if(type == null) {
			type = new ArrayList<>();
			types.put(asset.getType(), type);
		}
		types.get(asset.getType()).add(asset);
		names.put(asset.getName(), asset);
	}
	public Collection<Asset> getAssets() {
		return names.values();
	}

	public Asset getWithName(String name) {
		var res = names.get(name);
		if(res == null)throw new IllegalArgumentException("asset not found name=" + name);
		return res;
	}

	public Collection<Asset> getWithType(String type) {
		return types.get(type);
	}

	public InputStream openWithName(String name) {
		return getWithName(name).getData();
	}

}
