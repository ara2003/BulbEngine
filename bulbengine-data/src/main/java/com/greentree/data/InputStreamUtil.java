package com.greentree.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class InputStreamUtil {

	public static String readString(InputStream input) {
		return new String(readBytes(input));
	}

	public static byte[] readBytes(InputStream data) {
		try {
			return data.readAllBytes();
		}catch(IOException e) {
			throw new RuntimeException();
		}
	}


	public static void copy(InputStream source, OutputStream target) throws IOException {
		Objects.requireNonNull(source);
		Objects.requireNonNull(target);
		try(source; target) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = source.read(buf)) > 0) target.write(buf, 0, length);
		}
	}
}
