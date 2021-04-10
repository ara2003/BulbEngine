package com.greentree.engine;

import java.io.File;

import com.greentree.bulbgl.input.Input;
import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.render.SpriteRendener;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.Properties;
import com.greentree.engine.core.RootFiles;
import com.greentree.engine.system.CameraRenderSystem;

/** @author Arseny Latyshev */
public class Game3D extends GameCore {
	
	protected Game3D() {
	}
	
	public static void start(final String folder, final String[] args) {
		Properties.loadArguments(args);
		
		RootFiles.start(folder);
		Properties.loadProperty(new File(RootFiles.getRoot(), "config.game"));
		Windows.getWindow().setEventSystem(Events.getEventsystem());
		Input.setEventSystem(Events.getEventsystem());
		
		GameCore.setBuilder(new BasicXMlBuilder(Transform.class.getPackageName(), CircleColliderComponent.class.getPackageName(), Button.class.getPackageName(),
			CameraRenderSystem.class.getPackageName(), SpriteRendener.class.getPackageName()));
		
		GameCore.loadScene(Properties.getPropertyNotNull("scene.first"));
		
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().startRender();
			GameCore.gameLoop();
			Windows.getWindow().finishRender();
		}
	}
	
	
	
	public static void start(final String[] args) {
		Game3D.start("Game", args);
	}
	
}
