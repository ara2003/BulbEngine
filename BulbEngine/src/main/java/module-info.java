module bulbengine {
	
	requires transitive bulbengine.util;
	requires transitive bulbengine.geometry;
	requires transitive bulbengine.corutine;
	requires transitive org.joml;
	requires transitive bulbengine.graphics;
	requires bulbengine.event;
	requires org.lwjgl.opengl;
	requires org.lwjgl;
	requires org.lwjgl.glfw;
	requires java.desktop;
	requires java.net.http;
	requires com.google.common;
	
	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.collizion;
	exports com.greentree.engine.collizion.event;
	exports com.greentree.engine.collizion.collider;
	exports com.greentree.engine.system;
	exports com.greentree.engine.object;
}
