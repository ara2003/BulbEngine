
module bulbengine {

	requires transitive bulbengine.util;
	requires transitive bulbengine.geomentry;
	requires bulbengine.event;
	requires transitive org.joml;
	
	requires org.lwjgl.opengl;
	requires org.lwjgl;
	requires org.lwjgl.glfw;
	
	requires java.desktop;

	exports com.greentree.engine;
	exports com.greentree.engine.component;
	exports com.greentree.engine.component.ui;
	exports com.greentree.engine.component.collider;
	exports com.greentree.engine.system;
	exports com.greentree.engine.input;
	exports com.greentree.engine.gui;
	exports com.greentree.engine.corutine;
	
	
	
}