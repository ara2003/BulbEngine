package com.greentree.engine.system;

import java.util.List;

import com.greentree.engine.component.Phisic;
import com.greentree.engine.system.util.ISystem;

public class PhisicSystem extends ISystem<Phisic> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(final List<Phisic> list) {
		for(final Phisic b1 : list) for(final Phisic b2 : list) if(b1 != b2) {
		}
	}
}
