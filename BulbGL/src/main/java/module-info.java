
module bulbengine.graphics {

	requires bulbengine.util;
	requires bulbengine.event;
	requires bulbengine.geometry;
	
	requires org.lwjgl.opengl;
	requires org.lwjgl;
	requires org.lwjgl.glfw;
	
	requires transitive java.desktop;

	exports com.greentree.engine.bulbgl;
	exports com.greentree.engine.opengl;
	exports com.greentree.engine.opengl.rendener;
	exports com.greentree.engine.input.listeners;
	
	
}