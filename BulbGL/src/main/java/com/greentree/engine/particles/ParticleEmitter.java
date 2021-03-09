//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.particles;

import java.io.Serializable;

import com.greentree.engine.bulbgl.Image;

public interface ParticleEmitter extends Serializable {
	
	boolean completed();
	Image getImage();
	boolean isEnabled();
	boolean isOriented();
	void resetState();
	void setEnabled(final boolean p0);
	void update(final ParticleSystem p0, final int p1);
	void updateParticle(final Particle p0, final int p1);
	boolean useAdditive();
	boolean usePoints(final ParticleSystem p0);
	void wrapUp();
}
