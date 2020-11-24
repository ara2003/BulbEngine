//
// Decompiled by Procyon v0.5.36
//
package com.greentree.opengl;

import java.io.IOException;
import java.util.ArrayList;

public class CompositeIOException extends IOException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Exception> exceptions;

	public CompositeIOException() {
		exceptions = new ArrayList<>();
	}

	public void addException(final Exception e) {
		exceptions.add(e);
	}

	@Override
	public String getMessage() {
		String msg = "Composite Exception: \n";
		for(final Exception exception : exceptions) msg = msg + "\t" + exception.getMessage() + "\n";
		return msg;
	}
}
