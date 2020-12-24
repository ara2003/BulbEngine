package com.greentree.engine.script;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arseny Latyshev
 */
public class CodeBlock {

	private List<ScriptCommand> code;
	private ScriptContext context;

	public CodeBlock(String code, ScriptContext context) {
		this.context = context.clone();
		this.code = new ArrayList<>();
		for(String com : ((String) code.subSequence(code.indexOf('{')+1, code.length()-1)).split(";")) {
			this.code.add(new ScriptCommand(com, this.context));
		}
	}
	
	public void run() {
	}
	
	@Override
	public String toString() {
		return "CodeBlock [code=" + code + "]";
	}
	
	
}
