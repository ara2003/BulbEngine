
open module bulbengine.graphics {

	requires bulbengine.util;
	requires transitive bulbengine.event;

	requires transitive java.desktop;

	exports com.greentree.bulbgl;
	exports com.greentree.bulbgl.shader;
	exports com.greentree.bulbgl.texture;
	
	exports com.greentree.bulbgl.input;
	exports com.greentree.bulbgl.input.event;
	exports com.greentree.bulbgl.input.listener;
	
}