package com.greentree.bulbgl.texture;

/**
 * @author Arseny Latyshev
 *
 */
public interface Texture2D extends Texture {

	int getWidth();
	int getHeight();
	float getTexWidth();
	float getTexHeight();
	void setWrapX(Wrapping repeat);
	void setWrapY(Wrapping repeat);
	
	@Override
	default void setWrap(Wrapping repeat) {
		setWrapX(repeat);
		setWrapY(repeat);
	}
}
