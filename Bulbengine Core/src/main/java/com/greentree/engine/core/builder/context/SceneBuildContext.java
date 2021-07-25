package com.greentree.engine.core.builder.context;

import java.io.InputStream;

import com.greentree.engine.core.object.GameScene;

public abstract class SceneBuildContext implements BuildContext {
	protected final GameScene scene;
	protected final InputStream in;

	public SceneBuildContext(String sceneName, GameScene parent, InputStream in) {
		if(parent == null)
			scene = new GameScene(sceneName);
		else
			scene = new GameScene(sceneName, parent);
		
		this.in = in;
	}

	public abstract GameScene fill();

	public GameScene getScene() {
		return scene;
	}



}
