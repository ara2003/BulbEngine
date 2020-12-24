package com.greentree.engine.script;

/**
 * @author Arseny Latyshev
 *
 */
public class Function {

	private String name;
	private CodeBlock code;

	public Function(String text, ScriptContext context0) {
		System.out.println(text);
		ScriptContext context = context0.clone();
		for(String par : text.substring(text.indexOf('('), text.indexOf(')')).split(",")) {
			String[] words = par.split(" ");
			if(words.length != 2)throw new IllegalArgumentException(par);
			context.add(context.getType(words[0]), words[1]);
		}
		name = text.substring(0, text.indexOf('('));
		code = new CodeBlock(text.substring(text.indexOf(')')+1), context);
	}

	@Override
	public String toString() {
		return "Function [name=" + name + ", code=" + code + "]";
	}
	
}
