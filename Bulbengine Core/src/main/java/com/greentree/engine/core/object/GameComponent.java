package com.greentree.engine.core.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.greentree.common.ClassUtil;
import com.greentree.common.logger.Log;

public abstract class GameComponent {

	private boolean isDestoy = false;

	GameObject object;



	public final GameComponent copy() {
		GameComponent c = ClassUtil.newInstance(getClass());
		for(Field f : ClassUtil.getAllFields(getClass())) if((f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) == 0)if(!"object".equals(f.getName())) try {
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

	public final void destroy() {
		if(!isDestoy) {
			destroy_full();
			isDestoy = true;
		}
	}

	final void destroy_full() {
		object.removeComponent(this);
		object = null;
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

	@Override
	public String toString() {
		if(object != null)return super.toString() + "{" + object.toSimpleString() + "}";
		return super.toString()+"{null}";
	}

}
