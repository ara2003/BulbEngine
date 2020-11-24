//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine;

import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;

public final class Log {

	private static boolean forcedVerbose;
	private static PrintStream out, err;
	static {
		Log.out = System.out;
		Log.err = System.err;
		Log.forcedVerbose = false;
	}

	private Log() {
	}

	public static void checkVerboseLogSetting() {
		try {
			AccessController.doPrivileged((PrivilegedAction<Object>) ()-> {
				final String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
				if(val != null && val.equalsIgnoreCase("true")) Log.setForcedVerboseOn();
				return null;
			});
		}catch(final Throwable t) {
		}
	}

	public static void debug(final String message) {
		Log.out.println(new Date() + " DEBUG:" + message);
	}

	public static void error(final String message) {
		Log.err.println(new Date() + " ERROR:" + message);
		Game.exit();
	}
	
	public static void error(final String message, final Throwable e) {
		Log.error(message);
		Log.error(e);
		Game.exit();
	}
	
	public static void error(final Throwable e) {
		Log.err.println(new Date() + " ERROR:" + e.getMessage());
		e.printStackTrace(Log.err);
		Game.exit();
	}
	
	public static void info(final String message) {
		Log.out.println(new Date() + " INFO:" + message);
	}

	public static void setForcedVerboseOn() {
		Log.forcedVerbose = true;
	}

	public static void setVerbose(final boolean v) {
		if(Log.forcedVerbose) return;
	}
	
	public static void warn(final String message) {
		Log.err.println(new Date() + " WARN:" + message);
	}
	
	public static void warn(final String message, final Throwable e) {
		Log.warn(message);
		e.printStackTrace(Log.err);
	}
}
