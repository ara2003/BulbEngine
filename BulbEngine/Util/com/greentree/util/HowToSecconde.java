package com.greentree.util;

public class HowToSecconde implements Runnable {

	private int i;
	
	public synchronized void DO() {
		i++;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			}catch(final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			i = 0;
		}
	}
}
