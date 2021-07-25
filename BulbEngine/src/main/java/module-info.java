open module bulbengine {

	requires transitive bulbengine.core;
	requires transitive bulbengine.geometry;
	requires transitive bulbengine.math;
	requires transitive bulbengine.graphics;
	requires transitive bulbengine.util;
	requires transitive bulbengine.data;
	requires mylan;

	exports com.greentree.engine;
	exports com.greentree.engine.util;
	exports com.greentree.engine.system;
	exports com.greentree.engine.layer;
	exports com.greentree.engine.component;
	exports com.greentree.engine.render.ui;
	exports com.greentree.engine.render;
}
