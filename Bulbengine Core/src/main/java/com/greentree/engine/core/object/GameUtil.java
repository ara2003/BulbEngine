package com.greentree.engine.core.object;

public class GameUtil {

	private static void destroy(GameObject object, boolean in) {
		if(object.isDestroy())return;
		for(var c : object.childrens)destroy(c, true);
		object.childrens.clear();
		for(var c : object.components)c.destroy();
		object.components.clear();
		if(in)return;
		object.allTreeComponents.clear();
		object.getParent().updateUpTreeComponents();
	}
	
	public static void destroy(GameObject object) {
		destroy(object, false);
	}

}
