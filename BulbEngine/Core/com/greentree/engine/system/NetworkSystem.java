package com.greentree.engine.system;

import com.greentree.engine.Log;
import com.greentree.engine.component.NetworkComponent;
import com.greentree.engine.system.util.GameSystem;
import com.greentree.web.Client;
import com.greentree.web.Message;
import com.greentree.web.Server;
import com.greentree.web.WebContext;


/**
 * @author Arseny Latyshev
 *
 */
public class NetworkSystem extends GameSystem {
	private static final long serialVersionUID = 1L;
	
	Client cliet;
	
	@Override
	protected void start() {
		cliet = new Client("localhost", 3343) {
			
			@Override
			protected void serverDisconected() {
				Log.warn("Server disconected");
			}
			
			@Override
			public Message getMessage() {
				return new Message(0, getComponents(NetworkComponent.class).getObjects());
			}
			
			@Override
			protected void createServer(String host, int port) {
				new Server(port);
			}
			
			@Override
			public void EventMassage(WebContext message) {
				message.getData();
			}
		};
	}
	
	@Override
	public void execute() {
		getComponents(NetworkComponent.class);
	}
	
}
