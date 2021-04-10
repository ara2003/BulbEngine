package com.greentree.common.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Log {
	
	private static PrintStream log, err, bedug;
	
	protected Log() {
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

	public static boolean question(final String question) {
		return "y".equals(question(question, "y", "n"));
	}
	
	public static String question(final String question, String...statuses) {
		Arrays.asList(statuses).parallelStream().forEach(s -> {
			if(s == null)throw new IllegalArgumentException("status cannot be null");
			if(s.isBlank())throw new IllegalArgumentException("status cannot be blank");
		});
		System.out.printf("%s%s:", question, Arrays.toString(statuses));
		try(final Scanner sc = new Scanner(System.in)) {
			String line;
			do {
				System.out.println(Thread.currentThread());
				line = sc.nextLine();
				for(String s : statuses) {
					if(s.equals(line)) {
						System.out.println("-"+line);
						return line;
					}
				}
			}while(true);
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

	public static void IError(final Throwable e) {
	}
	
}
