package com.greentree.engine.system.bootstrap;

import com.greentree.engine.core.SceneMananger;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.node.GameSystem.MultiBehaviour;


public class LoadScene extends MultiBehaviour {

	@EditorData
	private String file;

	@Override
	protected void start() {
		SceneMananger.loadScene(file);
	}

}
