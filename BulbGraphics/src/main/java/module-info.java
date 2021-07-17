
module bulbengine.graphics {
	
	requires transitive org.lwjgl;
	requires org.lwjgl.opengl;
	requires org.lwjgl.glfw;
	
	requires bulbengine.util;

	requires transitive bulbengine.action;
	
	requires transitive org.joml;
	requires transitive java.desktop;
	requires bulbengine.math;
	requires bulbengine.data;

	exports com.greentree.graphics;
	exports com.greentree.graphics.input;
	exports com.greentree.graphics.window;
	exports com.greentree.graphics.shader;
	exports com.greentree.graphics.texture;
	
}
