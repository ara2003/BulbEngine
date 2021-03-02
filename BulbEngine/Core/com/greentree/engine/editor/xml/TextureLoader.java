package com.greentree.engine.editor.xml;

import java.io.IOException;

import com.greentree.engine.opengl.InternalTextureLoader;
import com.greentree.engine.opengl.Texture;
import com.greentree.engine.opengl.rendener.SGL;

/** @author Arseny Latyshev */
public class TextureLoader extends ClassLoader<Texture> {
	
	@Override
	public Texture load(String value) throws Exception {
		try {
			return InternalTextureLoader.get().getTexture(value, false, SGL.GL_NEAREST, null);
		}catch(IOException e) {
		}
		return null;
	}
}
