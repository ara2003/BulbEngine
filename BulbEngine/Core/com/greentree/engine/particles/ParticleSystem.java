//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.particles;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;

import com.greentree.engine.Log;
import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Image;
import com.greentree.engine.opengl.TextureImpl;
import com.greentree.opengl.rendener.Renderer;
import com.greentree.opengl.rendener.SGL;

public class ParticleSystem implements Serializable {
	
	public static final int BLEND_ADDITIVE = 1;
	
	public static final int BLEND_COMBINE = 2;
	private static final long serialVersionUID = 1L;
	private int blendingMode;
	private String defaultImageName;
	protected transient Particle dummy;
	protected ArrayList<ParticleEmitter> emitters;
	protected SGL GL;
	private Color mask;
	protected int maxParticlesPerEmitter;
	protected HashMap<ParticleEmitter, ParticlePool> particlesByEmitter;
	private int pCount;
	private boolean removeCompletedEmitters;
	private final ArrayList<ParticleEmitter> removeMe;
	private Image sprite;
	private boolean usePoints;
	private boolean visible;
	private float x;
	private float y;
	public ParticleSystem(final Image defaultSprite) {
		this(defaultSprite, 100);
	}
	
	public ParticleSystem(final Image defaultSprite, final int maxParticles) {
		GL = Renderer.get();
		removeMe = new ArrayList<>();
		particlesByEmitter = new HashMap<>();
		emitters = new ArrayList<>();
		blendingMode = 2;
		removeCompletedEmitters = true;
		visible = true;
		maxParticlesPerEmitter = maxParticles;
		sprite = defaultSprite;
		dummy = createParticle(this);
	}
	
	public ParticleSystem(final String defaultSpriteRef) {
		this(defaultSpriteRef, 100);
	}
	
	public ParticleSystem(final String defaultSpriteRef, final int maxParticles) {
		this(defaultSpriteRef, maxParticles, null);
	}
	
	public ParticleSystem(final String defaultSpriteRef, final int maxParticles, final Color mask) {
		GL = Renderer.get();
		removeMe = new ArrayList<>();
		particlesByEmitter = new HashMap<>();
		emitters = new ArrayList<>();
		blendingMode = 2;
		removeCompletedEmitters = true;
		visible = true;
		maxParticlesPerEmitter = maxParticles;
		this.mask = mask;
		setDefaultImageName(defaultSpriteRef);
		dummy = createParticle(this);
	}
	
	public void addEmitter(final ParticleEmitter emitter) {
		emitters.add(emitter);
		final ParticlePool pool = new ParticlePool(this, maxParticlesPerEmitter);
		particlesByEmitter.put(emitter, pool);
	}
	
	protected Particle createParticle(final ParticleSystem system) {
		return new Particle(system);
	}
	
	public int getBlendingMode() {
		return blendingMode;
	}
	
	public ParticleEmitter getEmitter(final int index) {
		return emitters.get(index);
	}
	
	public int getEmitterCount() {
		return emitters.size();
	}
	
	public Particle getNewParticle(final ParticleEmitter emitter, final float life) {
		final ParticlePool pool = particlesByEmitter.get(emitter);
		final ArrayList<Particle> available = pool.available;
		if(available.size() > 0) {
			final Particle p = available.remove(available.size() - 1);
			p.init(emitter, life);
			p.setImage(sprite);
			return p;
		}
		Log.warn("Ran out of particles (increase the limit)!");
		return dummy;
	}
	
	public int getParticleCount() {
		return pCount;
	}
	
	public float getPositionX() {
		return x;
	}
	
	public float getPositionY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	private void loadSystemParticleImage() {
		AccessController.doPrivileged((PrivilegedAction<Object>) ()-> {
			if(ParticleSystem.this.mask != null)
				ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName, ParticleSystem.this.mask);
			else ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName);
			return null;
		});
	}
	
	public void moveAll(final ParticleEmitter emitter, final float x, final float y) {
		final ParticlePool pool = particlesByEmitter.get(emitter);
		for(final Particle particle : pool.particles) if(particle.inUse()) particle.move(x, y);
	}
	
	public void release(final Particle particle) {
		if(particle != dummy) {
			final ParticlePool pool = particlesByEmitter.get(particle.getEmitter());
			pool.available.add(particle);
		}
	}
	
	public void releaseAll(final ParticleEmitter emitter) {
		if(!particlesByEmitter.isEmpty()) for(final ParticlePool pool : particlesByEmitter.values())
			for(final Particle particle : pool.particles) if(particle.inUse() && (particle.getEmitter() == emitter)) {
				particle.setLife(-1.0f);
				release(particle);
			}
	}
	
	public void removeAllEmitters() {
		for(int i = 0; i < emitters.size(); --i, ++i) removeEmitter(emitters.get(i));
	}
	
	public void removeEmitter(final ParticleEmitter emitter) {
		emitters.remove(emitter);
		particlesByEmitter.remove(emitter);
	}
	
	public void render() {
		this.render(x, y);
	}
	
	public void render(final float x, final float y) {
		if((sprite == null) && (defaultImageName != null)) loadSystemParticleImage();
		if(!visible) return;
		GL.glTranslatef(x, y, 0.0f);
		if(blendingMode == 1) GL.glBlendFunc(770, 1);
		if(usePoints()) {
			GL.glEnable(2832);
			TextureImpl.bindNone();
		}
		for(final ParticleEmitter emitter : emitters) if(emitter.isEnabled()) {
			if(emitter.useAdditive()) GL.glBlendFunc(770, 1);
			final ParticlePool pool = particlesByEmitter.get(emitter);
			Image image = emitter.getImage();
			if(image == null) image = sprite;
			if(!emitter.isOriented() && !emitter.usePoints(this)) image.startUse();
			for(final Particle particle : pool.particles) if(particle.inUse()) particle.render();
			if(!emitter.isOriented() && !emitter.usePoints(this)) image.endUse();
			if(emitter.useAdditive()) GL.glBlendFunc(770, 771);
		}
		if(usePoints()) GL.glDisable(2832);
		if(blendingMode == 1) GL.glBlendFunc(770, 771);
		Color.white.bind();
		GL.glTranslatef(-x, -y, 0.0f);
	}
	
	public void reset() {
		for(final ParticlePool pool : particlesByEmitter.values()) pool.reset(this);
		for(final ParticleEmitter emitter : emitters) emitter.resetState();
	}
	
	public void setBlendingMode(final int mode) {
		blendingMode = mode;
	}
	
	public void setDefaultImageName(final String ref) {
		defaultImageName = ref;
		sprite = null;
	}
	
	public void setPosition(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setRemoveCompletedEmitters(final boolean remove) {
		removeCompletedEmitters = remove;
	}
	
	public void setUsePoints(final boolean usePoints) {
		this.usePoints = usePoints;
	}
	
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
	
	public void update(final int delta) {
		if((sprite == null) && (defaultImageName != null)) loadSystemParticleImage();
		removeMe.clear();
		final ArrayList<ParticleEmitter> emitters = new ArrayList<>(this.emitters);
		for(int i = 0; i < emitters.size(); ++i) {
			final ParticleEmitter emitter = emitters.get(i);
			if(emitter.isEnabled()) {
				emitter.update(this, delta);
				if(removeCompletedEmitters && emitter.completed()) {
					removeMe.add(emitter);
					particlesByEmitter.remove(emitter);
				}
			}
		}
		this.emitters.removeAll(removeMe);
		pCount = 0;
		if(!particlesByEmitter.isEmpty())
			for(final ParticleEmitter emitter : particlesByEmitter.keySet()) if(emitter.isEnabled()) {
				final ParticlePool pool = particlesByEmitter.get(emitter);
				for(final Particle particle : pool.particles) if(particle.life > 0.0f) {
					particle.update(delta);
					++pCount;
				}
			}
	}
	
	public boolean usePoints() {
		return usePoints;
	}
	
	private class ParticlePool implements Serializable {
		
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		public transient ArrayList<Particle> available;
		public transient Particle[] particles;
		
		public ParticlePool(final ParticleSystem system, final int maxParticles) {
			particles = new Particle[maxParticles];
			available = new ArrayList<>();
			for(int i = 0; i < particles.length; ++i) particles[i] = createParticle(system);
			this.reset(system);
		}
		
		public void reset(final ParticleSystem system) {
			available.clear();
			for(final Particle particle : particles) available.add(particle);
		}
	}
}
