package com.greentree.engine.render.ui;

import com.greentree.action.Action;
import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.VectorAction3f;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.util.Events;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.input.listener.camera.CameraMouseAdapter;

/**
 * @deprecated use ClickComponent
 * @author User
 *
 */
@Deprecated
public class Button extends UIComponent {

	@EditorData
	private float border = 2;
	@Required
	@EditorData
	private String text;


	private float width, height;


	private final Action<ButtonListener> action = new Action<>();

	private VectorAction3f position;
	private boolean click0(final int x, final int y) {
		final AbstractVector2f vec = position.xy();
		if(x < vec.x() - width / 2 - border || x > vec.x() + width / 2 + border || y < vec.y() - height / 2 - border || y > vec.y() + height / 2 + border) return false;
		return true;
	}
	public Action<ButtonListener> getAction() {
		return action;
	}
	public String getText() {
		return text;
	}

	@Override
	public void render() {
		width = Graphics.getFont().getWidth(text);
		height = Graphics.getFont().getHeight(text);
		Graphics.setColor(1, 1, 1, 1);
		Graphics.getFont().drawString(position.x() - width / 2, position.y() - height / 2, text);
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void start() {
		position = getComponent(Transform.class).position;
		Events.addListener(new CameraMouseAdapter() {
			private static final long serialVersionUID = 1L;

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
