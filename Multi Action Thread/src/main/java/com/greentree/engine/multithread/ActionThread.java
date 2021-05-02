package com.greentree.engine.multithread;

import java.util.ArrayList;
import java.util.Collection;

/** @author Arseny Latyshev */
public class ActionThread {
	
	private final Collection<Runnable> tasks;
	private boolean runnable = true;
	private final Thread thread;
	
	public ActionThread(final String name, final float ups) {
		tasks  = new ArrayList<>();
		thread = new Thread(()-> {
			long lastStart = System.nanoTime();
			while(runnable) {
//				System.out.println((System.nanoTime() - lastStart) * ups / 1E9);
				if((System.nanoTime() - lastStart) * ups > 1E9) {
					lastStart = System.nanoTime();
					if(!tasks.isEmpty()) {
						for(final Runnable task : tasks) task.run();
						tasks.clear();
					}
				}
			}
		}, name);
		thread.start();
	}
	
	public void destroy() {
		while(!tasks.isEmpty()) {
			try {
				Thread.sleep(1);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}; 
		runnable = false;
	}
	
	public final void task(final Runnable task) {
		tasks.add(task);
	}
	
}
