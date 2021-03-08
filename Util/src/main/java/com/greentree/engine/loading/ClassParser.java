package com.greentree.engine.loading;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.Gson;


public class ClassParser {

	private static final Gson gson = new Gson();
	
	public static Object parse(final File file, final Class<?> clazz) {
		String text = null;
		try(DataInputStream in = new DataInputStream(new FileInputStream(file));) {
			text = new String(in.readAllBytes());
			in.close();
		}catch(final IOException e) {
			e.printStackTrace();
		}
		if(text == null) return null;
		return ClassParser.gson.fromJson(text, clazz);
	}

	public static void parse(final File file, final Object obj) {
		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(file));) {
			out.writeBytes(ClassParser.gson.toJson(obj));
			out.close();
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}
}
