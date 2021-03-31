package com.greentree.engine.component.ui;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.Graphics;
import com.greentree.engine.component.AbstractRendenerComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.EditorData;

public final class Text extends AbstractRendenerComponent {

	
	private static final long serialVersionUID = 1L;
	private Transform position;
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
		position = getComponent(Transform.class);
	}
	
	public void setText(final String text) {
		this.text = text;
	}
	
	@Override
	public void update() {
		this.w = Graphics.getFont().getWidth(this.text);
		this.h = Graphics.getFont().getHeight(this.text);
	}

	@Override
	public void render() {
		Graphics.setColor(color);
		Graphics.drawString(this.text, this.position.x() - this.w / 2, this.position.y() - this.h / 2);
	}
}
