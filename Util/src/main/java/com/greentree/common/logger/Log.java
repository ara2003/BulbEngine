package com.greentree.common.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

public abstract class Log extends Logger {

	private static PrintStream log, err, bedug;
	private static File folder;

	static {
		Logger.createType("debug", s->Log.bedug.println(s));
		Logger.createType("err", s->Log.err.println(s));
		Logger.createType("log", s->Log.log.println(s));
		Logger.createType("error", s -> {
			System.err.println(s);
			System.exit(-1);
		});
	}

	public static void createFileType(String type) throws FileNotFoundException {
		PrintStream stream = new PrintStream(new File(folder, type.toUpperCase() + ".txt"));
		createType(type, s -> stream.println(s));
	}

	public static void debug(final String message) {
		synchronized(Log.bedug) {
			Logger.print("bedug", message);
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
		Log.folder = folder;
		if(!folder.exists()) throw new IllegalArgumentException("folder " + folder.getAbsolutePath() + " is not exists");
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

	public static void warn(final String message, final Object... args) {
		Log.err.println(new Date() + " WARN:" + String.format(message, args));
	}

	public static void warn(final String message, final Throwable e) {
		Log.warn(message);
		Log.warn(e);
	}

	public static void warn(final Throwable e) {
		e.printStackTrace(Log.err);
	}

}
