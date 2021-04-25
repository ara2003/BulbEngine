
open module bulb3Dengine {
	
	requires transitive bulbengine.core;
	
	requires transitive bulbengine.geometry;
	requires transitive bulbengine.math;
	requires transitive bulbengine.graphics;
	requires com.google.common;
	
	exports com.greentree.engine;
	exports com.greentree.engine.collizion;
	exports com.greentree.engine.collizion.collider;

	exports com.greentree.engine.input;
	
	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.render;

	exports com.greentree.graphics.input.listener;
	exports com.greentree.graphics.input.event;
	exports com.greentree.graphics.input.listener.manager;
	
}
