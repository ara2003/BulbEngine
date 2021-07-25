package com.greentree.data.assets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.common.logger.Log;
import com.greentree.data.parse.ParserSet;

public class AssetHandlerList extends ParserSet<File, Asset, AssetHandler> {
	private static final long serialVersionUID = 1L;

	private final LoadingCache<File, List<AssetHandler>> cache = CacheBuilder.newBuilder().softValues()
			.build(new CacheLoader<>() {
				@Override
				public List<AssetHandler> load(final File file) throws Exception {
					return getParsers().parallelStream().filter(e->e.isLoadedFileType(file.getName().substring(file.getName().lastIndexOf('.')+1))).collect(Collectors.toList());
				}
			});

	@Override
	public void addParser(AssetHandler loader) {
		cache.cleanUp();
		super.addParser(loader);
	}
	
	@Override
	protected List<AssetHandler> getParsers(final File file) {
		try {
			return cache.get(file);
		}catch(final ExecutionException e) {
			Log.warn(e);
		}
		return new ArrayList<>();
	}
	
}
