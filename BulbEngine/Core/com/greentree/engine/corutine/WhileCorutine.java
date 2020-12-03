package com.greentree.engine.corutine;

import java.util.Objects;

public class WhileCorutine extends Corutine {
	
	private final CustomResemInstruction while1;
	
	public WhileCorutine(final Runnable runnable, final CustomResemInstruction wait,
			final CustomResemInstruction while1) {
		super(runnable, wait);
		this.while1 = Objects.requireNonNull(while1);
	}
	
	
	
	@Override
	public boolean run() {
		switch(run_type) {
			case start:
				runnable.run();
				run_type = runType.waiting;
			case waiting:
				if(while1.keepWaiting()) {
					if(wait.keepWaiting()) return false;
					else run_type = runType.start;
					return false;
				}
				run_type = runType.waitingNext;
			case waitingNext:
				run_type = runType.waiting;
				if(next == null) return true;
				else {
					final boolean res = next.run();
					if(!res) run_type = runType.waitingNext;
					return res;
				}
		}
		return true;
	}
}
