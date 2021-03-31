
open module bulbengine.graphics {

	requires bulbengine.util;
	requires bulbengine.event;
	requires bulbengine.geometry;
	
	requires org.lwjgl.opengl;
	requires org.lwjgl;
	requires org.lwjgl.glfw;

	requires transitive java.desktop;
	requires org.joml;

	exports com.greentree.bulbgl;
	exports com.greentree.bulbgl.image;
	exports com.greentree.bulbgl.image.animation;
	exports com.greentree.bulbgl.glfw;
	exports com.greentree.bulbgl.opengl.rendener;
	
	exports com.greentree.bulbgl.input;
	exports com.greentree.bulbgl.input.event;
	exports com.greentree.bulbgl.input.listener;
	
}