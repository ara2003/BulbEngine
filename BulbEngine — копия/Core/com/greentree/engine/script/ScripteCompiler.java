package com.greentree.engine.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arseny Latyshev
 *
 */
public class ScripteCompiler {

	ScriptContext mainContext;
	
	public ScripteCompiler(String code) {
		mainContext = new ScriptContext();
		code = code.replace('\n', ' ').replace('\t', ' ');
		List<Function> functions = init_functions(code);
		
		mainContext.addType("int", new Type() {
			
			@Override
			public byte[] getBytes() {
				return bytes;
			}

			@Override
			public int getCountBytes() {
				return 4;
			}

			@Override
			public String getName() {
				return "int";
			}
			
		});
		
	}
	
	private List<Function> init_functions(String code) {
		List<Function> fun = new ArrayList<>();		
		int i = 0, n = 0;
		while(n < code.length()) {
			i = n;
			n = code.indexOf('}', n)+1;
			String text = code.substring(i, Math.min(n+1, code.length()));
			Matcher m = Pattern.compile("\\p{Alpha}").matcher(text);
			int s = 0;
			if(m.find())s = m.start();
			fun.add(new Function(text.substring(s, text.indexOf('}')+1), mainContext));
		}
		return fun;
	}
	
	public ScripteCompiler(InputStream code) throws IOException {
		this(new String(code.readAllBytes()));
	}
	public ScripteCompiler(File code) throws IOException {
		this(new FileInputStream(code));
	}
	
	public void run() {
		
	}
	
}
