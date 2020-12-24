package com.greentree.engine.corutine;

public class ForCorutine extends Corutine {
	
	private int iterations;
	
	public ForCorutine(final Runnable runnable, final CustomResemInstruction wait, final int iterations) {
		super(runnable, wait);
		this.iterations = iterations;
	}
	
	@Override
	protected boolean waiting() {
		if(iterations > 1) {
			if(!wait.keepWaiting()) {
				iterations--;
				run_type = runType.start;
			}
			return true;
		}
		return false;
	}
	
}
