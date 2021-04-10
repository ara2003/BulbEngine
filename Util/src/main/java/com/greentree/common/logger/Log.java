//
// Decompiled by Procyon v0.5.36
//
package com.greentree.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

public final class Log {
	
	private static PrintStream log, err, bedug;
	
	private Log() {
	}
	
	public static void debug() {
		Log.debug("");
	}
	
	public static void debug(final String message) {
		synchronized(Log.bedug) {
			Log.bedug.println(message);
		}
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
		Log.error(e.getMessage(), e);
	}
	
	public static void info(final String message) {
		Log.log.println(new Date() + " INFO:" + message);
	}
	
	public static void info(final Throwable e) {
		e.printStackTrace(Log.log);
	}
	
	public static void init(final File folder) {
		if(!folder.exists()) throw new IllegalArgumentException("folder is not exists");
		if(!folder.isDirectory()) throw new IllegalArgumentException("folder is not directory");
		try {
			Log.log   = new PrintStream(new File(folder, "Log.txt"));
			Log.bedug = new PrintStream(new File(folder, "Debug.txt"));
			Log.err   = System.err;
		}catch(final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setForcedVerboseOn() {
	}
	
	public static void setLogFolder(final File folder) {
		Log.init(folder);
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
