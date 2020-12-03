package com.greentree.engine.corutine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import com.greentree.util.Pair;

public class Corutine {

	protected final Runnable runnable;
	protected final CustomResemInstruction wait;
	protected Corutine next;
	protected runType run_type = runType.start;
	
	public Corutine(final Runnable runnable, final CustomResemInstruction wait) {
		this.runnable = Objects.requireNonNull(runnable);
		this.wait = wait == null ? ()->false : wait;
	}
	
	public final Corutine addCorutine(final Corutine corutine) {
		if(next == null) next = corutine;
		else next.addCorutine(corutine);
		final Set<Corutine> used = new HashSet<>();
		Corutine c = corutine;
		while(c != null) {
			if(used.contains(c)) throw new IllegalArgumentException("Coroutine tree cannot contain loops");
			used.add(c);
			c = c.next;
		}
		return this;
	}
	
	/** @return is ended */
	public boolean run() {
		switch(run_type) {
			case start:
				runnable.run();
				run_type = runType.waiting;
			case waiting:
				if(wait.keepWaiting()) return false;
				run_type = runType.waitingNext;
			case waitingNext:
				run_type = runType.start;
				if(next == null) return true;
				else {
					final boolean res = next.run();
					if(!res) run_type = runType.waitingNext;
					return res;
				}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "[" + runnable + " " + wait + "]";//name
	}

	public String treeToString() {
		final List<Corutine> used = new ArrayList<>();
		final Stack<Pair<Corutine, Integer>> dfs = new Stack<>();
		dfs.add(new Pair<>(this, 0));
		Corutine current = null;
		String res = "";
		while(!dfs.isEmpty()) {
			final Pair<Corutine, Integer> p = dfs.pop();
			current = p.first;
			res += "\n";
			for(int i = 0; i < p.second; i++) res += "-";
			res += current;
			if(used.contains(current)) continue;
			used.add(current);
			if(current.next != null)
			dfs.add(new Pair<>(current.next, p.second + 1));
			//for(final Corutine corutine : current.next) dfs.add(new Pair<>(corutine, p.second + 1));
		}
		return res;
	}

	protected enum runType{
		waiting,start,waitingNext;
	}
}
