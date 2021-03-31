package com.greentree.engine.component;

import com.greentree.bulbgl.image.animation.Animation;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.UpdatingGameComponent;

public class Animator extends UpdatingGameComponent {
	
	Animation anim;
	@EditorData(name = "file")
	String ref;
	Transform t;
	
	@Override
	public void update() {
		this.anim.draw(this.t.x(), this.t.y());
	}
}
