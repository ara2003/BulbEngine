package com.greentree.engine.component.ui;

import org.joml.Vector2f;

import com.greentree.bulbgl.Graphics;
import com.greentree.engine.Game;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.component.AbstractRendenerComponent;
import com.greentree.engine.component.RequireComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.input.CameraMouseAdapter;

@RequireComponent({Transform.class})
public class Button extends AbstractRendenerComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData(def = "2")
	private int border;
	private Transform position;
	@EditorData
	String text;
	private float width, height;
	
	private boolean click0(final int button, final int x, final int y) {
		Vector2f vec = Game.getMainCamera().WorldToCamera(position.getXY());
		if(x < vec.x - width / 2 - border)return false;
		if(x > vec.x + width / 2 + border)return false;
		if(y < vec.y - height / 2 - border)return false;
		if(y > vec.y + height / 2 + border)return false;
		return true;
	}
	
	@Override
	public void render() {
		Graphics.drawString(text, position.x - width / 2, -position.y - height / 2);
	}
	
	@Override
	public void start() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		position = getComponent(Transform.class);
		Game.addListener(new CameraMouseAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void mousePress(final int button, final int x, final int y) {
				if(click0(button, x, y)) Game.event(new ButtonEvent(Button.this, button));
			}
			
		});
	}
}
