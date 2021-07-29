package com.greentree.data.asset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.greentree.common.logger.Log;
import com.greentree.data.FileUtil;
import com.greentree.data.InputStreamUtil;
import com.greentree.data.asset.basic.AssetFinderList;
import com.greentree.data.asset.basic.AssetResavedList;

public abstract class AssetUtil {

	public static final String ASSET_FILE_EXTENSION = "asset";

	private static final AssetFinderList finders = new AssetFinderList();
	private static final AssetResavedList resaveds = new AssetResavedList();

	public static void addAssetHandler(AssetHandler finder) {
		finders.addParser(finder);
	}

	public static void addAssetResaved(AssetResaved resaved) {
		resaveds.addParser(resaved);
	}

	public static AssetContext getContext(File assets) {
		if(assets.exists()) {
			var context = new AssetContext();
			if(!assets.isDirectory())throw new IllegalArgumentException("is not directory " + assets);
			for(File file : FileUtil.getAllFile(assets)) {
				var res = parse(file);
				System.out.println(res);
				if(res != null) context.add(res);
			}
			return context;
		}
		throw new IllegalArgumentException("not exists " + assets);
	}

	public static void saveAsset(File asset, File to) {
		if(asset.exists()) {
			var context = new AssetContext();
			context.add(parse(asset));
			context.saveTo(to);
		}
		throw new IllegalArgumentException("not exists " + asset);
	}
	
	public static void saveAssets(File assets, File to) {
		getContext(assets).saveTo(to);
	}
	

	public static InputStream parse(Asset asset) {
		try {
			return resaveds.parse(asset);
		}catch(Exception e) {
			Log.warn(e);
		}
		return null;
	}

	private static Asset parse(File file) {
		try {
			return finders.parse(file);
		}catch(Exception e) {
			Log.warn(e);
		}
		return null;
	}

	static void save(File dir, Asset asset) {
		File to = new File(dir, asset.getName() + '.' + AssetUtil.ASSET_FILE_EXTENSION);

		var res = AssetUtil.parse(asset);
		if(res == null)return;
		if(to.exists())throw new IllegalAccessError("file " + to + " is exist");
		try {
			to.createNewFile();
		}catch(IOException e1) {
			e1.printStackTrace();
		}
		try(FileOutputStream out = new FileOutputStream(to);) {
			out.write(asset.getType().getBytes());
			out.write(' ');
			out.write(asset.getName().getBytes());
			out.write(' ');
			ByteBuffer byteBuffer = ByteBuffer.allocate(asset.getSubType().length * Integer.BYTES);        
	        IntBuffer intBuffer = byteBuffer.asIntBuffer();
	        intBuffer.put(asset.getSubType());
			out.write(byteBuffer.array());
			out.write('\n');
			InputStream in = asset.getData();
			InputStreamUtil.copy(in, out);
			in.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	static Asset load(File file) {
		String name = null, type = null;
		int[] arr = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			var line = br.readLine();
			int index = line.indexOf(' ');
			type = line.substring(0, index);
			line = line.substring(index+1);
			index = line.indexOf(' ');
			name = line.substring(0, index);
			line = line.substring(index+1);
			index = line.indexOf(' ');
			line = line.substring(index+1);
			
			ByteBuffer buf = ByteBuffer.allocate(line.length());
			buf.put(line.getBytes());
			arr = new int[buf.capacity() / Integer.BYTES];
			buf.position(0);
			buf.asIntBuffer().get(arr);		
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return new Asset(type, name, () -> {
			InputStream in = new FileInputStream(file);
			for(byte[] b = {1}; b[0] != '\n'; b = in.readNBytes(1));
			return in;
		}, arr);
	}
	
}
