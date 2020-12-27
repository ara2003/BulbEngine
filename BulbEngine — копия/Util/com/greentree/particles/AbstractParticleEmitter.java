package com.greentree.particles;

import com.greentree.engine.gui.Image;

/** @author ara */
public abstract class AbstractParticleEmitter implements ParticleEmitter {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected float x;
	protected float y;
	
	public AbstractParticleEmitter() {
	}
	
	public AbstractParticleEmitter(final int x, final int y) {
		setLocation(x, y);
	}
	
	@Override
	public boolean completed() {
		return false;
	}
	
	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isOriented() {
		return false;
	}
	
	@Override
	public void resetState() {
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
	}
	
	public void setLocation(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public abstract void update(ParticleSystem engine, int delta);
	@Override
	public abstract void updateParticle(Particle paramParticle, int paramInt);
	
	@Override
	public boolean useAdditive() {
		return false;
	}
	
	@Override
	public boolean usePoints(final ParticleSystem system) {
		return false;
	}
	
	@Override
	public void wrapUp() {
	}
}
