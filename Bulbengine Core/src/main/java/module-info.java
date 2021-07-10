
open module bulbengine.core {

	requires transitive bulbengine.util;
	requires transitive bulbengine.event;
	requires transitive bulbengine.data;
	requires com.google.common;

	exports com.greentree.engine.core.component;
	exports com.greentree.engine.core.system;
	exports com.greentree.engine.core.builder;
	exports com.greentree.engine.core.builder.context;
	exports com.greentree.engine.core.object;
	exports com.greentree.engine.core.util;
	exports com.greentree.engine.core;

}
