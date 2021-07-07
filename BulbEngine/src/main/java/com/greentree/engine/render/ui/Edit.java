package com.greentree.engine.render.ui;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.VectorAction3f;
import com.greentree.engine.KeyBoard;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.util.Events;
import com.greentree.engine.input.CameraMouseAdapter;
import com.greentree.graphics.Graphics;

/**
 * @deprecated use com.greentree.engine.component.ClickComponent
 * @author User
 *
 */
public class Edit extends UIComponent {

	@EditorData
	private float border = 2;
	@Required
	@EditorData(value = "default text")
	private String defaultText;
	@Required
	@EditorData
	private float width;
	@EditorData
	private float height;
	
	public String getText() {
		return textBuilder.toString();
	}
	
	public float getBorder() {
		return border;
	}
	
	public String getDefaultText() {
		return defaultText;
	}
	private final StringBuilder textBuilder = new StringBuilder("");
	private VectorAction3f position;
	
	private boolean click0(final int x, final int y) {
		final AbstractVector2f vec = position.xy();
		if(x < vec.x() - width / 2 - border) return false;
		if(x > vec.x() + width / 2 + border) return false;
		if(y < vec.y() - height / 2 - border) return false;
		if(y > vec.y() + height / 2 + border) return false;
		return true;
	}
	@Override
	public void render() {
		String str;
		if(textBuilder.toString().isBlank()) str = defaultText;
		else str = textBuilder.toString();
		height = Graphics.getFont().getHeight(str);
		Graphics.disableBlead();
		Graphics.setColor(1, 1, 1, 1);
		Graphics.fillRect(position.x() - width / 2, position.y() - height / 2, width, height);
		Graphics.setColor(0, 0, 0, 1);
		Graphics.drawRect(position.x() - width / 2, position.y() - height / 2, width, height);
		Graphics.enableBlead();
		Graphics.setColor(.7, .7, .7, 1);
		Graphics.getFont().drawString(position.x() - width / 2 + 1, position.y() - height / 2, str);
	}
	@Override
	public void start() {
		position = getComponent(Transform.class).position;
		Events.addListener(new CameraMouseAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void mousePress(final int button, final int x, final int y) {
				if(Edit.this.click0(x, y)) KeyBoard.addString(textBuilder);
				else KeyBoard.removeString(textBuilder);
			}

		});
	}

}
