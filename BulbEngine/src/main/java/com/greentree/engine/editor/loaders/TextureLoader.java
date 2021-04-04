package com.greentree.engine.editor.loaders;

import java.io.IOException;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.TextureLoaderI;
import com.greentree.bulbgl.texture.Texture.Filtering;
import com.greentree.engine.editor.xml.CachingAbstractLoader;
import com.greentree.bulbgl.texture.Texture2D;

/** @author Arseny Latyshev */
public class TextureLoader extends CachingAbstractLoader<Texture2D> {
	
	private final static TextureLoaderI TL = BulbGL.getTextureLoader();
	
	public TextureLoader() {
		super(Texture2D.class);
	}
	
	@Override
	public Texture2D load0(final String value) throws IOException {
		Texture2D t = TL.getTexture2D(value);
		t.setMinFilter(Filtering.NEAREST);
		t.setMagFilter(Filtering.LINEAR);
		return t;
	}
}
