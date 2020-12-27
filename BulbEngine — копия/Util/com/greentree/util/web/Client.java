package com.greentree.util.web;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public abstract class Client {

	private ObjectInputStream in;
	private Thread inThread, outThread;
	private int name, pink;
	private ObjectOutputStream out;
	private Socket socket;

	public Client(final String host, final int port) {
		try {
			try {
				socket = new Socket(host, port);
			}catch(final ConnectException e) {
				createServer(host, port);
				try {
					socket = new Socket(host, port);
				}catch(final ConnectException e1) {
					System.err.println("mothod create Server don\'t create Server");
				}
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			inThread = new Thread((Runnable) ()-> {
				try {
					WebContext message = (WebContext) in.readObject();
					try {
						setName((int) message.getData());
					}catch(ClassCastException | NullPointerException e) {
						EventMassage(message);
					}
					while(!socket.isClosed()) try {
						message = (WebContext) in.readObject();
						EventMassage(message);
					}catch(final SocketException e) {
						System.err.println("Server disconected " + e.getLocalizedMessage());
						close();
						serverDisconected();
					}
					in.close();
				}catch(final IOException e) {
					e.printStackTrace();
				}catch(final ClassNotFoundException e) {
					e.printStackTrace();
				}
			}, "client in");
			outThread = new Thread((Runnable) ()-> {
				while(socket.isConnected()) {
					try {
						Thread.sleep(90);
					}catch(final InterruptedException e) {
						e.printStackTrace();
					}
					sendMessage();
				}
			}, "client out");
			System.out.println("Client connected to socket.");
		}catch(final IOException e) {
			e.printStackTrace();
		}
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

	protected abstract void createServer(String host, int port);
	public abstract void EventMassage(WebContext message);
	public abstract Message getMessage();

	public int getPink() {
		return pink;
	}

	public final void sendMessage() {
		final Message m = getMessage();
		if(m != null) sendMessage(m);
	}
	
	public final void sendMessage(final Message m) {
		final WebContext message = new WebContext(name, m);
		message.send();
		if(socket.isClosed()) {
			close();
			serverDisconected();
		}
		try {
			out.writeObject(message);
			out.flush();
		}catch(final IOException e) {
			serverDisconected();
		}
	}

	protected abstract void serverDisconected();

	public void setName(final int name) {
		this.name = name;
	}
	
	public void setPink(final int delta) {
		pink = delta;
	}

	public void start() {
		inThread.start();
		outThread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		inThread.stop();
		outThread.stop();
	}
}
