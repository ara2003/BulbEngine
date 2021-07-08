package com.greentree.engine.core.component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.object.GameObject;

public abstract class GameComponent {

	@Override
	public String toString() {
		if(object != null)return super.toString() + "{" + object.toSimpleString() + "}";
		return super.toString()+"{null}";
	}

	private boolean isDestoy = false;
	private GameObject object;
	public final GameComponent copy() {
		GameComponent c = ClassUtil.newInstance(getClass());
		for(Field f : ClassUtil.getAllFields(getClass())) if((f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) == 0)if(!f.getName().equals("object")) try {
			boolean flag1 = f.canAccess(c);
			boolean flag2 = f.canAccess(this);
			if(flag1 != flag2)throw new RuntimeException();
			f.setAccessible(true);
			f.set(c, f.get(this));
			f.setAccessible(flag1);
		}catch(IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return c;
	}

	public final boolean destroy() {
		if(isDestroy()) return true;
		isDestoy = true;
		object.removeComponent(this);
		object = null;
		return false;
	}

	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}

	public final GameObject getObject() {
		return object;
	}

	public final boolean isDestroy() {
		return isDestoy;
	}

	public final void setObject(final GameObject object) {//TODO
		if(this.object == null)
			this.object = object;
		else
			if(this.object != object)
				Log.warn("object is not null " + this + " " + object + "<>" + this.object);
	}

}
