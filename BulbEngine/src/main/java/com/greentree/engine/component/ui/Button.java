package com.greentree.engine.component.ui;

import org.joml.Vector2f;

import com.greentree.action.Action;
import com.greentree.bulbgl.Graphics;
import com.greentree.engine.Cameras;
import com.greentree.engine.component.AbstractRendenerComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.Game;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.input.CameraMouseAdapter;

@RequireComponent({Transform.class})
public class Button extends AbstractRendenerComponent {
	
	private static final long serialVersionUID = 1L;
	@DefoultValue("2")
	@EditorData()
	private int border;
	private Transform position;
	@EditorData
	String text;
	private float width, height;
	private final Action<ButtonListener> action = new Action<>();
	
	private boolean click0(final int button, final int x, final int y) {
		final Vector2f vec = Cameras.getMainCamera().WorldToCamera(this.position.xy());
		if(x < vec.x - this.width / 2 - this.border) return false;
		if(x > vec.x + this.width / 2 + this.border) return false;
		if(y < vec.y - this.height / 2 - this.border) return false;
		if(y > vec.y + this.height / 2 + this.border) return false;
		return true;
	}
	
	@Override
	public void render() {
		Graphics.drawString(this.text, this.position.x() - this.width / 2, -this.position.y() - this.height / 2);
	}
	
	@Override
	public void start() {
		this.width    = Graphics.getFont().getWidth(this.text);
		this.height   = Graphics.getFont().getHeight(this.text);
		this.position = this.getComponent(Transform.class);
		Game.addListener(new CameraMouseAdapter() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void mousePress(final int button, final int x, final int y) {
				if(Button.this.click0(button, x, y)) action.action(l->l.click(button));
			}
		});
	}

	public Action<ButtonListener> getAction() {
		return action;
	}
}
