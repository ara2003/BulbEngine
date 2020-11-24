package com.greentree.engine.gui.ui;

import com.greentree.engine.Game;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.XmlData;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.input.MouseAdapter;

public class Button extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	@XmlData(def = "2")
	private int border;
	private Transform t;
	@XmlData
	String text;
	private float width, height;
	
	private boolean click0(final int button, final int x, final int y) {
		return (boolean) (button == 0
				? x > t.x - width / 2 - border && x < t.x + width / 2 + border && y > t.y - height / 2 - border
						&& y < t.y + height / 2 + border
				: 0);
	}

	@Override
	public void start() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		Game.getEventSystem().addListener(new MouseAdapter() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void mousePressed(final int button, final int x, final int y) {
				if(click0(button, x, y)) Game.getEventSystem().event(new ButtonEvent(Button.this, button));
			}
		});
	}
	
	@Override
	public final void update() {
		Graphics.drawString(text, t.x - width / 2, t.y - height / 2);
	}
}
