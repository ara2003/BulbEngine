package com.greentree.engine.component.ui;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.Graphics;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.Transform;
import com.greentree.engine.object.GameComponent;

public final class Text extends GameComponent {
	
	Transform t;
	@EditorData
	String text;
	private float w, h;
	
	@Override
	public void start() {
		w = Graphics.getFont().getWidth(text);
		h = Graphics.getFont().getHeight(text);
	}
	
	@Override
	public void update() {
		Graphics.setColor(Color.darkGray);
		Graphics.drawString(text, t.x - w / 2, t.y - h / 2);
	}
}
