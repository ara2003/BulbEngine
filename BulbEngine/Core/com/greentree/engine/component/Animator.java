package com.greentree.engine.component;

import com.greentree.engine.GameComponent;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.gui.Animation;

public class Animator extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	Animation anim;
	@EditorData(name = "file")
	String ref;
	Transform t;
	
	@Override
	public void update() {
		anim.draw(t.x, t.y);
	}
}
