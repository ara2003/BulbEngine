package com.greentree.engine.render.ui;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

public final class Text extends UIComponent {

	@EditorData
	private final Color color = Color.darkGray;
	@Required
	@EditorData
	private String text;
	private float h;

	public String getText() {
		return text;
	}

	@Override
	public void render() {
		float w = Graphics.getFont().getWidth(text);
		h = Graphics.getFont().getHeight(text);
		color.bind();
		Graphics.getFont().drawString(position.x() - w / 2, position.y() - h / 2, text);
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public void start() {
		super.start();
	}
}
