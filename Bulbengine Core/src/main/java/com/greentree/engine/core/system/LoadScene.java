package com.greentree.engine.core.system;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.core.util.SceneMananger;


public class LoadScene extends MultiBehaviour {

	@EditorData
	private String file;

	@Override
	protected void start() {
		SceneMananger.loadScene(file);
	}

}
