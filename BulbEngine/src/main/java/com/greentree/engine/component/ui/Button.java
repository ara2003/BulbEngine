package com.greentree.engine.component.ui;

import org.joml.Vector2f;

import com.greentree.action.Action;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.input.CameraMouseAdapter;
import com.greentree.graphics.Graphics;


@RequireComponent({Transform.class})
public class Button extends UIComponent {
	
	@DefoultValue("2")
	@EditorData()
	private float border;
	@EditorData
	String text;
	private float width, height;
	private final Action<ButtonListener> action = new Action<>();
	private boolean click0(final int x, final int y) {
		final Vector2f vec = this.position.xy();
		if(x < vec.x - this.width / 2 - this.border) return false;
		if(x > vec.x + this.width / 2 + this.border) return false;
		if(y < vec.y - this.height / 2 - this.border) return false;
		if(y > vec.y + this.height / 2 + this.border) return false;
		return true;
	}
	
	public Action<ButtonListener> getAction() {
		return this.action;
	}
	
	@Override
	public void render() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		Graphics.setColor(1, 1, 1, 1);
		Graphics.getFont().drawString(this.position.x() - this.width / 2, this.position.y() - this.height / 2, this.text);
	}
	
	@Override
	public void start() {
		super.start();
		Events.addListener(new CameraMouseAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void mousePress(final int button, final int x, final int y) {
				if(Button.this.click0(x, y)) Button.this.action.action(l->l.click(button));
			}
		});
	}
	
	@FunctionalInterface
	public interface ButtonListener {
		
		void click(int mouseButton);
		
	}
}
