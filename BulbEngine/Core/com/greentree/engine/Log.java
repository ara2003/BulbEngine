//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine;

import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;

public final class Log {
	
	private static PrintStream file;
	static {
		Log.file = Debug.getLogFile();
	}
	
	private Log() {
	}
	
	public static void checkVerboseLogSetting() {
		try {
			AccessController.doPrivileged((PrivilegedAction<Object>) ()-> {
				final String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
				if((val != null) && val.equalsIgnoreCase("true")) Log.setForcedVerboseOn();
				return null;
			});
		}catch(final Throwable t) {
		}
	}
	
	public static void debug(final String message) {
		Log.file.println(new Date() + " DEBUG:" + message);
	}
	
	public static void error(final String message) {
		Log.file.println(new Date() + " ERROR:" + message);
	}
	
	public static void error(final String message, final Throwable e) {
		Log.file.println(new Date() + " ERROR:" + message);
		Log.error(e);
	}
	
	public static void error(final Throwable e) {
		Log.file.println(new Date() + " ERROR:" + e.getMessage());
		e.printStackTrace(Log.file);
	}
	
	public static void info(final String message) {
		Log.file.println(new Date() + " INFO:" + message);
	}
	
	public static void setForcedVerboseOn() {
	}
	
	
	public static void warn(final Object message) {
		Log.warn(message.toString());
	}
	public static void warn(final Object message, final Throwable e) {
		Log.warn(message);
		Log.warn(e);
	}
	
	public static void warn(final String message) {
		Log.file.println(new Date() + " WARN:" + message);
	}
	public static void warn(final String message, final Throwable e) {
		Log.warn(message);
		Log.warn(e);
	}
	
	public static void warn(final Throwable e) {
		e.printStackTrace(Log.file);
	}
	/**
	 * @deprecated use warn
	 */
	@Deprecated
	public static void superWarn(String message) {
		Log.file.println(new Date() + " WARN:" + message);
	}

    /**
     * @deprecated use warn
     */
	@Deprecated
	public static void superWarn(Exception e) {
		Log.file.println(new Date() + " WARN:" + e);
		e.printStackTrace(Log.file);
	}
}
