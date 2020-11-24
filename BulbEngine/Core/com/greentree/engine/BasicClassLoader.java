package com.greentree.engine;

import com.greentree.engine.component.Transform;
import com.greentree.engine.gui.ui.Button;
import com.greentree.engine.system.ColliderSystem;

public class BasicClassLoader extends ClassLoader {

	@Override
	public Class<?> loadClass(final String name) throws ClassNotFoundException {
		try {
			return super.loadClass(name);
		}catch(final ClassNotFoundException e) {
		}
		try {
			return super.loadClass(Transform.class.getPackageName() + "." + name);
		}catch(final ClassNotFoundException e) {
		}
		try {
			return super.loadClass(Button.class.getPackageName() + "." + name);
		}catch(final ClassNotFoundException e) {
		}
		try {
			return super.loadClass(ColliderSystem.class.getPackageName() + "." + name);
		}catch(final ClassNotFoundException e) {
		}
		throw new ClassNotFoundException(name);
	}
}
