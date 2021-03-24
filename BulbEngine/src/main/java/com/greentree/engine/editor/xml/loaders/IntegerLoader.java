package com.greentree.engine.editor.xml.loaders;

import com.greentree.bulbgl.input.Input;
import com.greentree.engine.Game;
import com.greentree.engine.editor.xml.PairLoader;

/** @author Arseny Latyshev */
public class IntegerLoader extends PairLoader<Integer> {
	
	public IntegerLoader() {
		super(Integer.class, int.class);
	}
	
	@Override
	public Integer load(String value) {
		if(value.startsWith("key::")) return Input.getIndexOfKey(value.substring(5));
		if(value.equals("window::width")) return Game.getWindow().getWidth();
		if(value.equals("window::height")) return Game.getWindow().getHeight();
		return Integer.parseInt(value);
	}
}
