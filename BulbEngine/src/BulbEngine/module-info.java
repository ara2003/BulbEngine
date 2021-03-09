
module bulbengine {

	requires transitive bulbengine.util;
	requires transitive bulbengine.geometry;
	requires transitive bulbengine.corutine;
	requires transitive org.joml;
	requires bulbengine.event;
	requires bulbengine.graphics;
	
	requires org.lwjgl.opengl;
	requires org.lwjgl;
	requires org.lwjgl.glfw;
	
	requires java.desktop;

	exports com.greentree.engine;
	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.collider;
	exports com.greentree.engine.system;
	
	
	
}