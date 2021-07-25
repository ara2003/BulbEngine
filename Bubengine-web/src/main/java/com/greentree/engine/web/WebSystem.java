package com.greentree.engine.web;

import com.greentree.common.web.Client;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;


public class WebSystem extends MultiBehaviour {
	
	@EditorData
	public Client client;
	
	@Override
	protected void start() {
		
	}
	
	@Override
	public void update() {
		
	}
	
}
