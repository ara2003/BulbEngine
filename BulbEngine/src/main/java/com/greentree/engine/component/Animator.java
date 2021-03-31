package com.greentree.engine.component;

import com.greentree.bulbgl.image.animation.Animation;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.EditorData;

public class Animator extends GameComponent {
	
	Animation anim;
	@EditorData(name = "file")
	String ref;
	Transform t;
	
	@Override
	public void update() {
		this.anim.draw(this.t.x(), this.t.y());
	}
}
