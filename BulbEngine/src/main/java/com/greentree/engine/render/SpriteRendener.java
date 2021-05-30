package com.greentree.engine.render;

import org.lwjgl.system.MemoryStack;

import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.graphics.Color;
import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.GLType;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.Graphics.ClientState;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.texture.Filtering;
import com.greentree.graphics.texture.GLTexture2D;

public class SpriteRendener extends CameraRendenerComponent {

	private transient Transform position;
	@EditorData
	private float width, height;
	@EditorData(name = "image")
	protected GLTexture2D texture;

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	@Override
	public void render() {
		Graphics.pushMatrix();
		Graphics.translate(position.x()+width/2, position.y()+height/2, position.z());

		Graphics.enableBlead();

		Color.white.bind();

		texture.bind();
		final float w = .5f / getWidth(), h = .5f / getHeight();
		try(MemoryStack stack = MemoryStack.stackPush()){
			Graphics.glVertexPointer(2, GLType.FLOAT, stack.floats(0, 0, 0, -height, -width, -height, -width, 0));
			Graphics.glTexCoordPointer(2, GLType.FLOAT, stack.floats(w, h, w, texture.getTexHeight() - h, texture.getTexWidth() - w, texture.getTexHeight() - h, texture.getTexWidth() - w, h));
		}
		Graphics.glEnableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glEnableClientState(ClientState.TEXTURE_COORD_ARRAY);

		Graphics.glDrawArrays(GLPrimitive.QUADS, 0, 4);

		Graphics.glDisableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glDisableClientState(ClientState.TEXTURE_COORD_ARRAY);

		GLTexture2D.unbindTexture();
		Graphics.disableBlead();

		Graphics.popMatrix();
	}

	public void setHeight(final float height) {
		if(height <= 0) this.height = texture.getHeight();
		else this.height = height;
	}

	public void setSize(final float width, final float height) {
		this.width  = width;
		this.height = height;
	}

	public void setWidth(final float width) {
		if(width <= 0) this.width = texture.getWidth();
		else this.width = width;
	}

	@Override
	public void start() {
		texture.setMagFilter(Filtering.LINEAR);
		texture.setMinFilter(Filtering.NEAREST);

		texture.setWrap(Wrapping.CLAMP_TO_BORDER);
		position = this.getComponent(Transform.class);
		if(width == 0) width = texture.getWidth();
		if(height == 0) height = texture.getHeight();
	}
}
