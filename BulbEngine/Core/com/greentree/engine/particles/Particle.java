//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.particles;

import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Image;
import com.greentree.engine.opengl.TextureImpl;
import com.greentree.opengl.rendener.Renderer;
import com.greentree.opengl.rendener.SGL;

public class Particle {
	
	protected static SGL GL = Renderer.get();
	public static final int INHERIT_POINTS = 1;
	public static final int USE_POINTS = 2;
	public static final int USE_QUADS = 3;
	protected Color color;
	private ParticleEmitter emitter;
	private final ParticleSystem engine;
	protected Image image;
	protected float life;
	protected boolean oriented;
	protected float originalLife;
	protected float scaleY;
	protected float size;
	protected int type;
	protected int usePoints;
	protected float velx;
	protected float vely;
	protected float x, y;
	
	public Particle(final ParticleSystem engine) {
		size = 10.0f;
		color = Color.white;
		usePoints = 1;
		oriented = false;
		scaleY = 1.0f;
		this.engine = engine;
	}
	
	public void adjustColor(final float r, final float g, final float b, final float a) {
		if(color == Color.white) color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		final Color color = this.color;
		color.r += r;
		final Color color2 = this.color;
		color2.g += g;
		final Color color3 = this.color;
		color3.b += b;
		final Color color4 = this.color;
		color4.a += a;
	}
	
	public void adjustColor(final int r, final int g, final int b, final int a) {
		if(color == Color.white) color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		final Color color = this.color;
		color.r += r / 255.0f;
		final Color color2 = this.color;
		color2.g += g / 255.0f;
		final Color color3 = this.color;
		color3.b += b / 255.0f;
		final Color color4 = this.color;
		color4.a += a / 255.0f;
	}
	
	public void adjustLife(final float delta) {
		life += delta;
	}
	
	public void adjustPosition(final float dx, final float dy) {
		x += dx;
		y += dy;
	}
	
	public void adjustSize(final float delta) {
		size += delta;
		size = Math.max(0.0f, size);
	}
	
	public void adjustVelocity(final float dx, final float dy) {
		velx += dx;
		vely += dy;
	}
	
	public Color getColor() {
		return color;
	}
	
	public ParticleEmitter getEmitter() {
		return emitter;
	}
	
	public float getLife() {
		return life;
	}
	
	public float getOriginalLife() {
		return originalLife;
	}
	
	public float getScaleY() {
		return scaleY;
	}
	
	public float getSize() {
		return size;
	}
	
	public int getType() {
		return type;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void init(final ParticleEmitter emitter, final float life) {
		x = 0.0f;
		this.emitter = emitter;
		y = 0.0f;
		velx = 0.0f;
		vely = 0.0f;
		size = 10.0f;
		type = 0;
		this.life = life;
		originalLife = life;
		oriented = false;
		scaleY = 1.0f;
	}
	
	public boolean inUse() {
		return life > 0.0f;
	}
	
	public boolean isOriented() {
		return oriented;
	}
	
	public void kill() {
		life = 1.0f;
	}
	
	public void move(final float x, final float y) {
		this.x += x;
		this.y += y;
	}
	
	public void render() {
		if((engine.usePoints() && (usePoints == 1)) || (usePoints == 2)) {
			TextureImpl.bindNone();
			Particle.GL.glEnable(2832);
			Particle.GL.glPointSize(size);
			color.bind();
			Particle.GL.glBegin(0);
			Particle.GL.glVertex2f(x, y);
			Particle.GL.glEnd();
		}else if(oriented || (scaleY != 1.0f)) {
			Particle.GL.glPushMatrix();
			Particle.GL.glTranslatef(x, y, 0.0f);
			if(oriented) {
				final float angle = (float) ((Math.atan2(y, x) * 180.0) / 3.141592653589793);
				Particle.GL.glRotatef(angle, 0.0f, 0.0f, 1.0f);
			}
			Particle.GL.glScalef(1.0f, scaleY, 1.0f);
			image.draw((int) -(size / 2.0f), (int) -(size / 2.0f), (int) size, (int) size, color);
			Particle.GL.glPopMatrix();
		}else {
			color.bind();
			image.drawEmbedded((int) (x - (size / 2.0f)), (int) (y - (size / 2.0f)), (int) size, (int) size);
		}
	}
	
	public void setColor(final float r, final float g, final float b, final float a) {
		if(color == Color.white) color = new Color(r, g, b, a);
		else {
			color.r = r;
			color.g = g;
			color.b = b;
			color.a = a;
		}
	}
	
	public void setImage(final Image image) {
		this.image = image;
	}
	
	public void setLife(final float life) {
		this.life = life;
	}
	
	public void setOriented(final boolean oriented) {
		this.oriented = oriented;
	}
	
	public void setPosition(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setScaleY(final float scaleY) {
		this.scaleY = scaleY;
	}
	
	public void setSize(final float size) {
		this.size = size;
	}
	
	public void setSpeed(final float speed) {
		final float currentSpeed = (float) Math.sqrt((velx * velx) + (vely * vely));
		velx *= speed;
		vely *= speed;
		velx /= currentSpeed;
		vely /= currentSpeed;
	}
	
	public void setType(final int type) {
		this.type = type;
	}
	
	public void setUsePoint(final int usePoints) {
		this.usePoints = usePoints;
	}
	
	public void setVelocity(final float velx, final float vely) {
		this.setVelocity(velx, vely, 1.0f);
	}
	
	public void setVelocity(final float dirx, final float diry, final float speed) {
		velx = dirx * speed;
		vely = diry * speed;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + life;
	}
	
	public void update(final int delta) {
		emitter.updateParticle(this, delta);
		life -= delta;
		if(life > 0.0f) {
			x += delta * velx;
			y += delta * vely;
		}else engine.release(this);
	}
}
