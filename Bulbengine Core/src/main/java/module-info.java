
open module bulbengine.core {

	requires transitive bulbengine.util;
	requires transitive bulbengine.action;
	requires transitive bulbengine.data;
	requires com.google.common;

	exports com.greentree.engine.core.builder;
	exports com.greentree.engine.core.builder.context;
	exports com.greentree.engine.core.node;
	exports com.greentree.engine.core;

}
