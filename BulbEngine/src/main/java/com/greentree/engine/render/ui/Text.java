package com.greentree.engine.render.ui;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

public final class Text extends UIComponent {
	
	@EditorData
	private final Color color = Color.darkGray;
	@EditorData(required = true)
	private String text;
	private float w, h;
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public void render() {
		this.w = Graphics.getFont().getWidth(this.text);
		this.h = Graphics.getFont().getHeight(this.text);
		this.color.bind();
		Graphics.getFont().drawString(this.position.x() - this.w / 2, this.position.y() - this.h / 2, this.text);
	}
	
	public void setText(final String text) {
		this.text = text;
	}
	
	@Override
	public void start() {
		super.start();
	}
}
