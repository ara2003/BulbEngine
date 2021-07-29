package com.greentree.data.asset;

import java.io.InputStream;

import com.greentree.data.parse.Parser;

public interface AssetResaved extends Parser<Asset, InputStream> {

	boolean isSaveType(String type);

	@Override
	InputStream parse(Asset asset) throws Exception;
}
