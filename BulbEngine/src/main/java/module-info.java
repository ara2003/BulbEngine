open module bulbengine {

	requires transitive bulbengine.core;
	requires transitive bulbengine.geometry;
	requires transitive bulbengine.math;
	requires transitive bulbengine.graphics;
	requires com.google.common;
	requires com.google.gson;
	requires bulbengine.util;
	requires org.joml;
	requires bulbengine.data;
	requires mylan;

	exports com.greentree.engine;
	exports com.greentree.engine.layer;
	exports com.greentree.engine.component;
	exports com.greentree.engine.render.ui;
	exports com.greentree.engine.render;
}
