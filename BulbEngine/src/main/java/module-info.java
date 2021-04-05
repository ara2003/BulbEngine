
open module bulbengine {
	
	requires transitive bulbengine.core;

	requires transitive bulbengine.geometry;
	requires transitive bulbengine.math;
	requires transitive bulbgl.glfw;
	requires org.lwjgl;
	requires org.lwjgl.opengl;

	exports com.greentree.engine;
	exports com.greentree.engine.collizion;
	exports com.greentree.engine.collizion.collider;

	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.render;
	
}
