package com.greentree.engine;

import com.greentree.common.concurent.MultyTask;
import com.greentree.common.time.Time;
import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.util.RootFiles;
import com.greentree.engine.core.util.SceneMananger;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Game3D extends GameCore {

	private static final String BOOTSTRAP_SCENE = "bootstrap-scene";

	public static void exit() {
		Windows.getWindow().shouldClose();
	}

	public static void start(final String folder, final String[] args) {
		GameCore.addArgumentConflict("-run", "-build");
		GameCore.addArguments(args);
		RootFiles.start(folder);

		GameCore.setBuilder(new BasicXMlBuilder());
		{
			GameScene scene = SceneMananger.loadScene(BOOTSTRAP_SCENE);
			if(SceneMananger.isCurrent(scene)) throw new UnsupportedOperationException("the boot-strap scene has not changed");
		}

		Mouse.init();
		KeyBoard.init();

		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().swapBuffer();
			Graphics.glClearAll();
			Windows.getWindow().updateEvents();
			Time.updata();
			SceneMananger.getCurrentScene().update();
		}
		MultyTask.shutdown();
		BulbGL.terminate();
	}

	public static void start(final String[] args) {
		Game3D.start("Game", args);
	}
}
