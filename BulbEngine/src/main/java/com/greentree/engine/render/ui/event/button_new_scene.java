package com.greentree.engine.render.ui.event;

import com.greentree.engine.Mouse;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.engine.core.util.SceneMananger;
import com.greentree.engine.render.ui.Button;

public class button_new_scene extends StartGameComponent {

	@Required
	@EditorData(value = "scene")
	private String sceneName;

	@Override
	public void start() {
		getComponent(Button.class).getAction().addListener(new Runnable() {
			private boolean on = true;

			@Override
			public void run() {
				if(!Mouse.isPressedMouseButton(0)) return;
				if(on) {
					SceneMananger.loadScene(sceneName);
					on = false;
				}
			}

		});
	}
}
