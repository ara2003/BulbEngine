package com.greentree.common.concurent;

import java.util.function.Supplier;

/**
 * @author Arseny Latyshev
 *
 */
@Deprecated
public class Task<R> {
	
	private R result;
	private Thread thread;
	
	public Task(Supplier<R> supplier) {
		thread = new Thread(() -> result = supplier.get());
	}
	
	public R get() {
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
