package com.greentree.engine;

import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.util.SceneMananger;
import com.greentree.engine.system.SaveSystem;

public abstract class SaveUtil {

	protected static GameSystem saveSystem = null;

	protected static SaveSystem getSaveSystem() {
		if(saveSystem != null && !saveSystem.isDestroy()) return (SaveSystem) saveSystem.getBehaviour();
		saveSystem = SceneMananger.getCurrentSceneNotNull().getSystem(SaveSystem.class);
		return (SaveSystem) saveSystem.getBehaviour();
	}

	public static Object load(String name) {
		return getSaveSystem().load(name);
	}

	public static Object remove(String name) {
		return getSaveSystem().remove(name);
	}
	public static Object save(String name, Object object) {
		return getSaveSystem().save(name, object);
	}

}
