package com.greentree.engine;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

@Deprecated
public final class Debug {
	
	private static FileWriter time, event;
	static {
		FileManager.getDirectory("Debug");
		try {
			time = new FileWriter(FileManager.getFile("Debug\\time.txt"));
		}catch(final IOException e) {
			time = null;
			Log.error(e);
		}
		try {
			event = new FileWriter(FileManager.getFile("Debug\\event.txt"));
		}catch(final IOException e) {
			event = null;
			Log.error(e);
		}
	}
	
	private Debug() {
	}
	
	public static void addEvent(final String name, final String data) {
		try {
			event.write(Time.getTime() + "~" + name + "~" + data + "\n");
		}catch(final IOException e) {
			Log.error(e);
		}
	}
	
	public static void addTime(final String name, final String data) {
		try {
			time.write(Time.getTime() + "~" + name + "~" + data + "\n");
		}catch(final IOException e) {
			Log.error(e);
		}
	}

	public static PrintStream getLogFile() {
		try {
			return new PrintStream(FileManager.getFile("Debug\\Log.txt"));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
