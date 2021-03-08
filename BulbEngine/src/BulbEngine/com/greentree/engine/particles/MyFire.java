package com.greentree.engine.particles;

public class MyFire extends AbstractParticleEmitter {
	
	private static final long serialVersionUID = 1L;
	private final int interval = 15, wInterval = 80;
	private int timer, wTimer;
	
	@Override
	public void update(final ParticleSystem system, final int delta) {
		timer -= delta;
		wTimer -= delta;
		if(timer <= 0) {
			timer = interval;
			int iter = 4;
			while(iter-- > 0) {
				final Particle p = system.getNewParticle(this, 500.0F);
				p.setColor(1.0F, 1.0F, 1.0F, 0.5F);
				p.setPosition((float) (x + (100 * Math.random())), y);
				p.setSize(8);
				final float vx = delta * (float) ((0.04f * Math.random()) + 0.01f);
				final float vy = delta * -(float) ((0.04f * Math.random()) + 0.01f);
				p.setVelocity(vx, vy, 1.0F);
			}
		}
	}
	
	@Override
	public void updateParticle(final Particle particle, final int delta) {
		if(wTimer <= 0) //particle.adjustVelocity((float) (Math.pow(particle.getY()*2, 4)), 0);
			wTimer = wInterval;
		particle.velx *= 0.95f;
		if(particle.getY() > 200) particle.life = 0;
	}
}
