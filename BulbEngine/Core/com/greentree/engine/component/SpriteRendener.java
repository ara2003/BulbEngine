package com.greentree.engine.component;

import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.XmlData;
import com.greentree.engine.component.util.render;
import com.greentree.engine.gui.Image;

@render
public class SpriteRendener extends GameComponent {

	private static final long serialVersionUID = 1L;
	@XmlData
	private String image;
	private transient Image img;
	private transient Transform t;
	@XmlData
	private int width, height;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	@Override
	protected void start() {
		if(img == null) {
			img = new Image(image);
			if(width == 0) width = img.getWidth();
			if(height == 0) height = img.getHeight();
		}
	}
	
	@Override
	public void update() {
		img.draw(t.x - width / 2, t.y - height / 2, width, height);
	}
}
