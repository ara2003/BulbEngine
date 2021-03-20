//
// Decompiled by Procyon v0.5.36
//
package com.greentree.bulbgl.image.util;

public class ImageDataFactory {
	
	private static boolean usePngLoader = true;
	
	public static LoadableImageData getImageDataFor(String ref) {
		ref = ref.toLowerCase();
		if(ref.endsWith(".tga")) return new TGAImageData();
		if(ref.endsWith(".png")) {
			final CompositeImageData data = new CompositeImageData();
			if(ImageDataFactory.usePngLoader) data.add(new PNGImageData());
			data.add(new ImageIOImageData());
			return data;
		}
		return new ImageIOImageData();
	}
}
