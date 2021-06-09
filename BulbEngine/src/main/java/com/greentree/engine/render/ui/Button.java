package com.greentree.engine.render.ui;

import org.joml.Vector2f;

import com.greentree.action.Action;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.input.CameraMouseAdapter;
import com.greentree.graphics.Graphics;

/**
 * @deprecated use ClickComponent
 * @author User
 *
 */
@RequireComponent({Transform.class})
@Deprecated
public class Button extends UIComponent {

	@EditorData()
	private final float border = 2;

	@EditorData(required = true)
	String text;
	private float width, height;
	private final Action<ButtonListener> action = new Action<>();
	private boolean click0(final int x, final int y) {
		final Vector2f vec = position.xy();
		if(x < vec.x - width / 2 - border) return false;
		if(x > vec.x + width / 2 + border) return false;
		if(y < vec.y - height / 2 - border) return false;
		if(y > vec.y + height / 2 + border) return false;
		return true;
	}

	public Action<ButtonListener> getAction() {
		return action;
	}

	@Override
	public void render() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		Graphics.setColor(1, 1, 1, 1);
		Graphics.getFont().drawString(position.x() - width / 2, position.y() - height / 2, text);
	}

	@Override
	public void start() {
		super.start();
		Events.addListener(new CameraMouseAdapter() {

			@Override
			public void mousePress(final int button, final int x, final int y) {
				if(Button.this.click0(x, y)) action.action(l->l.click(button));
			}
		});
	}

	@FunctionalInterface
	public interface ButtonListener {

		void click(int mouseButton);
	}
}
