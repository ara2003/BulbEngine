package com.greentree.engine.core.system;

import com.greentree.engine.core.SceneLoader;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.object.GameSystem;


public class LoadScene extends GameSystem {
	
	@EditorData
	private String file;
	
	@Override
	protected void start() {
		SceneLoader.loadScene(file);
	}
	
}
