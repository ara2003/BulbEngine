package com.greentree.common.logger;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

/** @author Arseny Latyshev */
public abstract class Logger {

	private static final Map<String, Consumer<String>> types = new HashMap<>();

	static {
		Logger.createType("crate logger type", System.out::println);
		Logger.createType("warn", System.err::println);
	}

	public static void createType(String type, final Consumer<String> consumer) {
		if(Objects.requireNonNull(type).isBlank()) throw new IllegalArgumentException("type cannot be blank");
		type = type.toUpperCase();
		Objects.requireNonNull(consumer);
		if(Logger.types.get(type) != null) throw new UnsupportedOperationException("this \"type\" " + type + " already exists");
		Logger.types.put(type, consumer);
		Logger.print("crate logger type", type);
	}

	public static void print(String type, final String string, final Object... obj) {
		if(Objects.requireNonNull(type).isBlank()) throw new IllegalArgumentException("type cannot be blank");
		type = type.toUpperCase();
		final var a = Logger.types.get(type);
		if(a == null) {
			Logger.createType(type, System.out::println);
			Logger.print("warn", "this type \"" + type + "\" does not exist. The registrar will create an empty type");
			Logger.print(type, string, obj);
			return;
			//			throw new UnsupportedOperationException("this \"type\" " + type + " does not exist");
		}
		a.accept(String.format("%s %s : %s", new Date(), type.toUpperCase(), String.format(string, obj)));
	}

	public static boolean question(final String question) {
		return "y".equals(question(question, "y", "n"));
	}

	public static String question(final String question, String...statuses) {
		Arrays.asList(statuses).parallelStream().forEach(s -> {
			if(s == null)throw new IllegalArgumentException("status cannot be null");
			if(s.isBlank())throw new IllegalArgumentException("status cannot be blank");
		});
		System.out.println(question+" "+Arrays.toString(statuses));
		try(final Scanner sc = new Scanner(System.in)) {
			String line;
			do try {
				line = sc.nextLine();
				for(String s : statuses) if(s.equals(line)) return line;
			}catch (Exception e) {
				print("warn", e.getMessage());
			}
			while(true);
		}
	}


}
