package com.greentree.engine.corutine;

import java.util.Objects;

public class WhileCorutine extends Corutine {
	
	private final CustomResemInstruction while1;
	
	public WhileCorutine(final Runnable runnable, final CustomResemInstruction wait, final CustomResemInstruction while1) {
		super(runnable, wait);
		this.while1 = Objects.requireNonNull(while1);
	}
	
	@Override
	protected boolean waiting() {
		if(while1.keepWaiting()) {
			if(wait.keepWaiting()) return true;
			else run_type = runType.start;
			return true;
		}
		return false;
	}
	
}
