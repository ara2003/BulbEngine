
open module bulbengine.core {

	requires transitive bulbengine.corutine;
	requires transitive bulbengine.util;
	requires transitive bulbengine.event;
	requires com.google.common;
	
	exports com.greentree.engine.core.component;
	exports com.greentree.engine.core.system;
	exports com.greentree.engine.core.builder;
	exports com.greentree.engine.core;
	
}
