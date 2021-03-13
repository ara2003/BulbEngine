//
// Decompiled by Procyon v0.5.36
//
package com.greentree.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;

public final class Log {
	
	private static PrintStream log, err, bedug;

	public static void setLogFolder(File folder) {
		init(folder);
	}
	
	public static void init(File folder) {
		if(!folder.exists())throw new IllegalArgumentException("folder is not exists");
		if(!folder.isDirectory())throw new IllegalArgumentException("folder is not directory");
		try {
			log = new PrintStream(new File(folder, "Log.txt"));
			bedug = new PrintStream(new File(folder, "Debug.txt"));
			err = System.err;
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
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
		Log.log.println(new Date() + " DEBUG:" + message);
	}
	
	public static void error(final String message) {
		Log.err.println(new Date() + " ERROR:" + message);
		System.exit(-1);
	}
	
	public static void error(final String message, final Throwable e) {
		Log.err.println(new Date() + " ERROR:" + message);
		e.printStackTrace(Log.err);
		System.exit(-1);
	}
	
	public static void error(final Throwable e) {
		error(e.getMessage(), e);
	}
	
	public static void info(final String message) {
		Log.log.println(new Date() + " INFO:" + message);
	}
	
	public static void info(Throwable e) {
		e.printStackTrace(Log.log);
	}
	
	public static void setForcedVerboseOn() {
	}
	
	/** @deprecated use warn */
	@Deprecated
	public static void superWarn(String message) {
		Log.log.println(new Date() + " WARN:" + message);
	}
	
	/** @deprecated use warn */
	@Deprecated
	public static void superWarn(Throwable e) {
		Log.log.println(new Date() + " WARN:" + e);
		e.printStackTrace(Log.log);
	}
	
	public static void warn(final Object message) {
		Log.warn(message.toString());
	}
	
	public static void warn(final Object message, final Throwable e) {
		Log.warn(message);
		Log.warn(e);
	}
	
	public static void warn(final String message) {
		Log.err.println(new Date() + " WARN:" + message);
	}
	
	public static void warn(final String message, final Throwable e) {
		Log.warn(message);
		Log.warn(e);
	}
	
	public static void warn(final Throwable e) {
		e.printStackTrace(Log.err);
	}

}
