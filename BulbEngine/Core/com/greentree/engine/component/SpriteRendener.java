package com.greentree.engine.component;

import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.render;
import com.greentree.engine.gui.Image;

@render
public class SpriteRendener extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	
	@EditorData
	private String image;
	private transient Image img;
	private transient Transform t;
	@EditorData
	private int width, height;
	private int imgWidth, imgHeight;
	@EditorData(reserve = "scale")
	private RendenerType renderType;
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		imgWidth = width;
		imgHeight = height;
	}
	
	@Override
	protected void start() {
		img = new Image(image);
		imgWidth = width;
		imgHeight = height;
		if(imgWidth == 0)imgWidth = img.getWidth();
		if(imgWidth == 0)imgHeight = img.getHeight();
		if(renderType == RendenerType.horizontalCycle) {
			imgWidth = img.getWidth();
		}
		
	}
	
	@Override
	public void update() {
		switch(renderType) {
			case scale:
				img.draw(t.x - (imgWidth / 2), t.y - (imgHeight / 2), imgWidth, imgHeight);
				break;
			case horizontalCycle:
				for(int i = 0; i < (width / imgWidth); i++)
					img.draw((t.x - (imgWidth / 2)) + (imgWidth * i), t.y - (imgHeight / 2), imgWidth, imgHeight);
				break;
				
		}
	}
	
	public enum RendenerType{
		horizontalCycle, scale;
	}
}
