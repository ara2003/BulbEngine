
open module bulbengine.core {

	requires transitive bulbengine.corutine;
	requires transitive bulbengine.util;
	requires transitive bulbengine.graphics;
	requires transitive bulbengine.event;
	
	exports com.greentree.engine.core.component;
	exports com.greentree.engine.core.system;
	exports com.greentree.engine.core.editor;
	exports com.greentree.engine.core;
	
}
