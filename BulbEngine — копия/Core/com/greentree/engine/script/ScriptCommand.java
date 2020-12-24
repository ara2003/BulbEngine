package com.greentree.engine.script;


/**
 * @author Arseny Latyshev
 *
 */
public class ScriptCommand {

	public ScriptCommand(String com, ScriptContext context) {
		if(com == null)return;
		com = com.replace(" ", "");
		if(com.equals(""))return;
		
//		context.get();
		System.out.println("com: " + com);
	}
}
