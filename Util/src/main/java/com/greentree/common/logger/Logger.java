package com.greentree.common.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/** @author Arseny Latyshev */
public class Logger extends Log {
	
	private static final Map<String, Consumer<String>> types = new HashMap<>();
	
	static {
		Logger.createType("crate logger type", System.out::println);
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
			Logger.warn("this type \"" + type + "\" does not exist. The registrar will create an empty type");
			Logger.createType(type, s->{});
			Logger.print(type, string, obj);
			return;
			//			throw new UnsupportedOperationException("this \"type\" " + type + " does not exist");
		}
		a.accept(String.format("%s %s : %s", new Date(), type.toUpperCase(), String.format(string, obj)));
	}
	
}
