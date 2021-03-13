package com.greentree.engine.corutine;

import java.util.Objects;

public class Corutine {
	
	protected final Runnable runnable;
	protected final CustomResemInstruction wait;
	protected Corutine next, originalNext;
	protected runType run_type = runType.start;
	
	public Corutine(final Runnable runnable, final CustomResemInstruction wait) {
		this.runnable = Objects.requireNonNull(runnable);
		this.wait = wait == null ? ()->false : wait;
	}
	
	public final Corutine addCorutine(final Corutine corutine) {
		if(originalNext == null)originalNext = corutine;else
			originalNext.addCorutine(corutine);
		
		return this;
	}
	
	/** @return is ended */
	public final boolean run() {
		switch(run_type) {
			case start:
				runnable.run();
				run_type = runType.waiting;
			case waiting:
				if(waiting()) return false;
				if(next == null)next = originalNext;
				if(next == null) {
					end();
					return true;
				}
				run_type = runType.waitingNext;
			case waitingNext:
//				if(next.run_type == runType.waitingNext) {
//					next = next.next;
//				}
				if(next.run()) {
					end();
					return true;
				}else {
					
					return false;
				}
		}
		return true;
	}
	
	private final void end() {
		next = null;
		run_type = runType.start;
	}

	protected boolean waiting() {
		return wait.keepWaiting();
	}
	
	//	public String treeToString() {
	//		final List<Corutine> used = new ArrayList<>();
	//		final Stack<Pair<Corutine, Integer>> dfs = new Stack<>();
	//		dfs.add(new Pair<>(this, 0));
	//		Corutine current = null;
	//		String res = "";
	//		while(!dfs.isEmpty()) {
	//			final Pair<Corutine, Integer> p = dfs.pop();
	//			current = p.first;
	//			res += "\n";
	//			for(int i = 0; i < p.second; i++) res += "-";
	//			res += current;
	//			if(used.contains(current)) continue;
	//			used.add(current);
	//			if(current.next != null) dfs.add(new Pair<>(current.next, p.second + 1));
	//			//for(final Corutine corutine : current.next) dfs.add(new Pair<>(corutine, p.second + 1));
	//		}
	//		return res;
	//	}
	
	protected enum runType{
		waiting,start,waitingNext;
	}
}
