
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.opengl.GLGraphics;

open module bulbgl.glfw {
	
	requires transitive bulbengine.graphics;
	requires transitive bulbengine.geometry;

	requires transitive org.lwjgl;
	requires org.lwjgl.opengl;
	requires org.lwjgl.glfw;
	requires bulbengine.math;

	exports com.greentree.bulbgl.glfw;
	exports com.greentree.bulbgl.opengl;
	exports com.greentree.bulbgl.opengl.texture;
	exports com.greentree.bulbgl.opengl.shader;
	
	provides GraphicsI with GLGraphics;
	
}
