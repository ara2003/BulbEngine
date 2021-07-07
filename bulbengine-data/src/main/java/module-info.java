
open module bulbengine.data {
	
	requires com.google.gson;
	requires transitive bulbengine.util;
	requires transitive bulbengine.event;

	exports com.greentree.data;
	exports com.greentree.data.loaders;
	exports com.greentree.data.loaders.collection;
	exports com.greentree.data.loaders.value;
	exports com.greentree.data.loading;
	exports com.greentree.data.parse;
	exports com.greentree.data.serialize;
	exports com.greentree.data.assets;
	
}
