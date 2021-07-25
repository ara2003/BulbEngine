package com.greentree.engine.util;

import com.greentree.engine.core.SceneMananger;
import com.greentree.engine.system.SaveSystem;

public abstract class SaveUtil {

	protected static SaveSystem saveSystem = null;

	protected static SaveSystem getSaveSystem() {
		if(saveSystem != null && !saveSystem.isDestroy()) return saveSystem;
		saveSystem = SceneMananger.getCurrentSceneNotNull().getSystem(SaveSystem.class).getBehaviour();
		return saveSystem;
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
