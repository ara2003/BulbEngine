package com.greentree.engine.multithread;


/** @author Arseny Latyshev */
public class Test {
	
	static MultiThreadSystem system = new MultiThreadSystem();
	
	public static void main(final String[] args) {
		
		for(int i = 0; i < 100; i++) Test.start(i);
		
		Test.system.destroy();
		
	}
	
	private static void start(final int i) {
		Test.system.task("test " + (i % 2), ()-> {
			//			System.out.println("s");
			System.out.println("task " + i);
			//			System.out.println("f");
		});
	}
	
}
