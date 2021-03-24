package com.greentree.engine.component;

import com.greentree.bulbgl.image.animation.Animation;
import com.greentree.engine.object.GameComponent;

public class Animator extends GameComponent {
	
	Animation anim;
	@EditorData(name = "file")
	String ref;
	Transform t;
	
	@Override
	public void update() {
		anim.draw(t.x, t.y);
	}
}
