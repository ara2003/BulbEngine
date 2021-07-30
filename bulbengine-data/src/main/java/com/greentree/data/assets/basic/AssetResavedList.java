package com.greentree.data.assets.basic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.common.logger.Log;
import com.greentree.data.assets.Asset;
import com.greentree.data.assets.AssetResaved;
import com.greentree.data.parse.ParserSet;

public class AssetResavedList extends ParserSet<Asset, InputStream, AssetResaved> {
	private static final long serialVersionUID = 1L;

	private final LoadingCache<String, List<AssetResaved>> cache = CacheBuilder.newBuilder().softValues()
			.build(new CacheLoader<>() {
				@Override
				public List<AssetResaved> load(final String assetType) throws Exception {
					return getParsers().parallelStream().filter(e->e.isSaveType(assetType)).collect(Collectors.toList());
				}
			});

	@Override
	public void addParser(AssetResaved loader) {
		cache.cleanUp();
		super.addParser(loader);
	}
	@Override
	protected Collection<AssetResaved> getRightParsers(Asset value) {
		try {
			return cache.get(value.getType());
		}catch(final ExecutionException e) {
			Log.warn(e);
		}
		return new ArrayList<>();
	}
}
