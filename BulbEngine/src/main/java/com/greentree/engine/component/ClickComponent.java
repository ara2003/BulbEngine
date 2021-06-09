package com.greentree.engine.component;

import org.joml.Vector2f;

import com.greentree.action.Action;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.graphics.input.listener.MouseAdapter;

@RequireComponent({Transform.class})
public class ClickComponent extends StartGameComponent {

	@EditorData()
	private final float border = 2;
	@EditorData(required = true)
	private float width, height;

	private final Action<ButtonListener> action = new Action<>();

	private Transform position;
	private boolean click0(final int x, final int y) {
		final Vector2f vec = position.xy();
		if(x < vec.x - width / 2 - border) return false;
		if(x > vec.x + width / 2 + border) return false;
		if(y < vec.y - height / 2 - border) return false;
		if(y > vec.y + height / 2 + border) return false;
		return true;
	}

	@Override
	public void start() {
		position = getComponent(Transform.class);
		addListener(new MouseAdapter() {
			@Override
			public void mousePress(int button, int x, int y) {
				if(click0(x, y)) action.action(l->l.click(button));
			}
		});
	}

	@FunctionalInterface
	public interface ButtonListener {

		void click(int mouseButton);

	}



}
