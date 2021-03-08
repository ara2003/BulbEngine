package com.greentree.engine.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {
	
	private final Map<String, List<Image>> anim = new HashMap<>();
	private List<Image> currentAnim;
	private int fream;
	
	public Animation() {
		currentAnim = new ArrayList<>();
	}
	
	public void addToSteate(final String state, final Image img) {
		anim.get(state).add(img);
	}
	
	public void draw(final float x, final float y) {
		currentAnim.get(fream++ % currentAnim.size()).draw(x, y);
	}
	
	public void setSteate(final String state) {
		currentAnim = anim.get(state);
	}
}
