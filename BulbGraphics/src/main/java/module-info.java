
module bulbengine.graphics {
	
	requires transitive org.lwjgl;
	requires org.lwjgl.opengl;
	requires org.lwjgl.glfw;
	
	requires bulbengine.util;
	
	requires transitive bulbengine.event;
	
	requires transitive org.joml;
	requires bulbengine.math;
	requires transitive java.desktop;

	exports com.greentree.graphics;
	exports com.greentree.graphics.input;
	exports com.greentree.graphics.window;
	exports com.greentree.graphics.shader;
	exports com.greentree.graphics.texture;
	
}
