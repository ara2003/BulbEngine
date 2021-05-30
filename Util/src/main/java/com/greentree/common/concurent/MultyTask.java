package com.greentree.common.concurent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** @author Arseny Latyshev */
public abstract class MultyTask {

	private static final ExecutorService executor = Executors.newCachedThreadPool();

	public static void shutdown() {
		MultyTask.executor.shutdown();
	}

	public static <V> Future<V> task(final Callable<V> run) {
		return MultyTask.executor.submit(run);
	}

	public static void task(final Runnable run) {
		MultyTask.executor.submit(run);
	}

}
