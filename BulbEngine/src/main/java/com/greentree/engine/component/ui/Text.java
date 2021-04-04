package com.greentree.engine.component.ui;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.Graphics;
import com.greentree.engine.core.component.EditorData;

public final class Text extends UIComponent {

	@EditorData
	private Color color = Color.darkGray;
	@EditorData
	private String text;
	private float w, h;
	
	public String getText() {
		return this.text;
	}
	
	@Override
	protected void start() {
		super.start();
	}
	
	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public void render() {
		this.w = Graphics.getFont().getWidth(this.text);
		this.h = Graphics.getFont().getHeight(this.text);
		Graphics.setColor(color);
		Graphics.drawString(this.text, this.position.x() - this.w / 2, this.position.y() - this.h / 2);
	}
}
