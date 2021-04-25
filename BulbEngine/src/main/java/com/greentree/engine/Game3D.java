package com.greentree.engine;

import java.io.File;

import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.render.SpriteRendener;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.Properties;
import com.greentree.engine.core.RootFiles;
import com.greentree.engine.system.CameraRenderSystem;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Game3D extends GameCore {
	
	protected Game3D() {
	}
	
	public static void start(final String folder, final String[] args) {
		BulbGL.init();
		Properties.loadArguments(args);
		
		
		RootFiles.start(folder);
		Properties.loadProperty(new File(RootFiles.getRoot(), "config.game"));
		
		GameCore.setBuilder(new BasicXMlBuilder(Transform.class.getPackageName(), CircleColliderComponent.class.getPackageName(), Button.class.getPackageName(),
			CameraRenderSystem.class.getPackageName(), SpriteRendener.class.getPackageName()));
		
		Windows.getWindow().makeCurrent();

		Graphics.clearColor(.6f, .6f, .6f);
		Graphics.setClearDepth(1.0);
		
		Mouse.setMousePos(0, 0);
		KeyBoard.init();
		
		GameCore.loadScene(Properties.getPropertyNotNull("scene.first"));
		
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().swapBuffer();
			Graphics.glClearAll();
			Windows.getWindow().updateEvents();
			GameCore.gameLoop();
		}
		BulbGL.terminate();
	}
	
	public static void start(final String[] args) {
		Game3D.start("Game", args);
	}
	
}
