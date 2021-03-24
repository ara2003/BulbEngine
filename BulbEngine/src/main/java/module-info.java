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
	
	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.collider;
	exports com.greentree.engine.system;
	exports com.greentree.engine.object;
}
