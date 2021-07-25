package com.greentree.engine.component;

import com.greentree.action.RunAction;
import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.util.Cameras;
import com.greentree.engine.util.Windows;

@RequireComponent({Transform.class})
public class ClickComponent extends StartGameComponent {

	@EditorData()
	private final float border = 2;

	@Required
	@EditorData
	private float width, height;

	private final RunAction action = new RunAction();

	private Transform position;
	private boolean click0(final float x, final float y) {
		final AbstractVector2f vec = position.position.xy();
		if(x < vec.x() - width / 2 - border || x > vec.x() + width / 2 + border || y < vec.y() - height / 2 - border || y > vec.y() + height / 2 + border) return false;
		return true;
	}

	@Override
	public void start() {
		position = getComponent(Transform.class);
		Windows.getWindow().getMousePosition().addListener((xpos, ypos) -> {
			if(click0(Cameras.getMainCamera().WindowToCameraX(xpos), Cameras.getMainCamera().WindowToCameraY(ypos))) action.action();
		});
	}



}
