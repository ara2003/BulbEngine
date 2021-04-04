
open module bulbengine {
	
	requires transitive bulbengine.core;

	requires transitive bulbengine.geometry;
	requires transitive bulbgl.glfw;
	requires transitive org.joml;

	exports com.greentree.engine;
	exports com.greentree.engine.collizion;
	exports com.greentree.engine.collizion.collider;

	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.render;
	
}
