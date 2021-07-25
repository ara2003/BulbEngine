package com.greentree.engine.render.ui;

import com.greentree.action.RunAction;
import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.engine.Mouse;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.util.Cameras;
import com.greentree.engine.util.Windows;
import com.greentree.graphics.Graphics;

/**
 * @deprecated use ClickComponent
 * @author User
 *
 */
@Deprecated
public class Button extends UIComponent {

	@EditorData()
	private final float border = 2;
	private float width, height;

	private final RunAction action = new RunAction();

	private Transform position;

	@Required
	@EditorData
	private String text;
	private boolean click0(final float x, final float y) {
		final AbstractVector2f vec = position.position.xy();
		if(x < vec.x() - width / 2 - border || x > vec.x() + width / 2 + border || y < vec.y() - height / 2 - border || y > vec.y() + height / 2 + border) return false;
		return true;
	}

	public RunAction getAction() {
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
		Graphics.getFont().drawString(position.position.x() - width / 2, position.position.y() - height / 2, text);
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void start() {
		position = getComponent(Transform.class);
		Windows.getWindow().getMouseButtonPress().addListener(b -> {
			float x = Mouse.getMouseX();
			float y = Mouse.getMouseY();

			if(click0(Cameras.getMainCamera().WindowToCameraX(x), Cameras.getMainCamera().WindowToCameraY(y)) && Mouse.anyButtonPressed()) action.action();
		});
	}

}
