package com.greentree.engine.util;

import com.greentree.engine.core.SceneMananger;
import com.greentree.engine.system.TimeSystem;

public abstract class Time {

	protected static TimeSystem timeSystem = null;

	public static TimeSystem getTimeSystem() {
		if(timeSystem != null && !timeSystem.isDestroy()) return timeSystem;
		timeSystem = SceneMananger.getCurrentSceneNotNull().getSystem(TimeSystem.class).getBehaviour();
		return timeSystem;
	}

	public static int getFps() {
		return getTimeSystem().getFps();
	}

	public static float getDelta() {
		return getTimeSystem().getDelta();
	}

	public static float getTime() {
		return getTimeSystem().getTime();
	}
		
	
	
}
