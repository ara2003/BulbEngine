package com.greentree.common.web;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import com.greentree.action.EventAction;

public class Client {

	private final ObjectInputStream in;
	private final Thread inThread;
	private int name;
	private final ObjectOutputStream out;
	private final Socket socket;
	private final EventAction<WebContext> messageAction = new EventAction<>();

	public Client(final String host, final int port) throws UnknownHostException, IOException {
			try {
				socket = new Socket(host, port);
			}catch(final ConnectException e) {
				throw e;
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			inThread = new Thread((Runnable) ()-> {
				try {
					WebContext message = (WebContext) in.readObject();
					try {
						System.out.println("Client connected to socket.");
						name = (int) message.getData();
						System.out.println("name: " + name);
					}catch(ClassCastException | NullPointerException e) {
						EventMassage(message);
					}
					while(!socket.isClosed()) try {
						message = (WebContext) in.readObject();
						EventMassage(message);
					}catch(final SocketException e) {
						System.err.println("Server disconected " + e.getLocalizedMessage());
						close();
					}
					in.close();
				}catch(final IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}, "client in");
			inThread.start();
	}

	private void EventMassage(WebContext message) {
		messageAction.action(message);
	}

	public void close() {
		try {
			socket.close();
			out.close();
			in.close();
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}

	public final void sendMessage(final Message m) throws IOException {
		final WebContext message = new WebContext(name, m);
		if(socket.isClosed()) throw new IOException("socket is close");
		out.writeObject(message);
		out.flush();
	}

	public void addListener(Consumer<WebContext> e) {
		messageAction.addListener(e);
	}

}
