
open module bulbengine.data {
	
	requires com.google.gson;
	requires transitive bulbengine.util;

	exports com.greentree.data.loaders;
	exports com.greentree.data.loaders.value;
	exports com.greentree.data.loaders.collection;
	exports com.greentree.data.loading;
	exports com.greentree.data.parse;
	exports com.greentree.data.serialize;
	
}
