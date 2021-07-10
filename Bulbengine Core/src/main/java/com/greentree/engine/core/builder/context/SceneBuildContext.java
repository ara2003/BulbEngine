package com.greentree.engine.core.builder.context;

import java.io.InputStream;

import com.greentree.data.FileUtil;
import com.greentree.engine.core.object.GameScene;

public abstract class SceneBuildContext implements BuildContext {
	protected final GameScene scene;
	protected final InputStream in;

	static {
		FileUtil.addUseFileListener(c -> {
			System.out.println(c);
		});
	}
	
	public SceneBuildContext(String sceneName, InputStream in) {
		this.scene = new GameScene(sceneName);
		this.in = in;
	}

	public abstract GameScene fill();
	
	public GameScene getScene() {
		return scene;
	}
	
	
	
}
