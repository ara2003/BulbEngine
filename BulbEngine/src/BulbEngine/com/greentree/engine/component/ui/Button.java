package com.greentree.engine.component.ui;

import java.io.IOException;
import java.nio.CharBuffer;

import com.greentree.engine.Game;
import com.greentree.engine.GameComponent;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.RendenerComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.input.MouseAdapter;
import com.greentree.engine.opengl.rendener.SGL;

public class Button extends RendenerComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData(def = "2")
	private int border;
	private Transform position;
	@EditorData
	String text;
	private float width, height;
	
	private boolean click0(final int button, final int x, final int y) {
		if(button != 0)return false;
		return (x > (position.x - (width / 2) - border)) && (x < (position.x + (width / 2) + border)) && (y > (position.y - (height / 2) - border)) && (y < (position.y + (height / 2) + border));
	}
	
	@Override
	public void start() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		
		position = getComponent(Transform.class);
		
		Game.addListener(new MouseAdapter() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void mousePressed(final int button, final int x, final int y) {
				if(click0(button, x, y)) Game.event(new ButtonEvent(Button.this, button));
			}
		});
	}

	@Override
	public void render() {
		Graphics.drawString(text, position.x - (width / 2), position.y - (height / 2));
		
	}
}
