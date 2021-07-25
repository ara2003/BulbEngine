package com.greentree.graphics.texture;

import java.io.File;

import com.greentree.data.FileUtil;
import com.greentree.graphics.core.ImageIOImageData;

public enum ImageType {
	PNG {

		@Override
		public LoadableImageData get() {
			final CompositeImageData data = new CompositeImageData();
			data.add(new PNGImageData());
			data.add(new ImageIOImageData());
			return data;
		}
	}, TGA {

		@Override
		public LoadableImageData get() {
			return new TGAImageData();
		}
	}, JPG {

		@Override
		public LoadableImageData get() {
			return new ImageIOImageData();
		}
	}, JPEG {

		@Override
		public LoadableImageData get() {
			return new ImageIOImageData();
		}
	};
	
	public abstract LoadableImageData get();

	public static ImageType getImageType(File resource) {
		return ImageType.getImageType(resource.getName());
	}
	public static ImageType getImageType(String resourceName) {
		return ImageType.valueOf(FileUtil.getType(resourceName).toUpperCase());
	}
	
}
