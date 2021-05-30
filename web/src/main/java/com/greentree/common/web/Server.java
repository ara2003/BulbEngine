package com.greentree.common.web;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Server {

	private final class oneListener implements Runnable {

		private ObjectInputStream in;
		private final Object lock = new Object();
		protected ObjectOutputStream out;
		private final Socket socket;

		public oneListener(final int name, final Socket socket) {
			this.socket = socket;
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				sendMessage(new WebContext(-1, name));
			}catch(final IOException e) {
				e.printStackTrace();
			}
		}

		public void close() {
			try {
				socket.close();
				in.close();
				out.close();
			}catch(final IOException e) {
			}
		}

		@Override
		public void run() {
			try {
				WebContext message = null;
				while(!socket.isClosed()) try {
					message = (WebContext) in.readObject();
					addMessage(message);
				}catch(final SocketException e) {
					System.err.println("Server disconected " + e.getLocalizedMessage());
					close();
				}
				in.close();
			}catch(final IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		public void sendMessage(final WebContext context) throws IOException {
			synchronized(lock) {
				try {
					out.writeObject(context);
					out.flush();
				}catch(final IOException e) {
					in.close();
					out.close();
					throw new WebException("server cloes");
				}
			}
		}
	}

	private final Object lock = new Object();
	private final CopyOnWriteArrayList<oneListener> mons = new CopyOnWriteArrayList<>();

	public Server(final int port) {
		new Thread((Runnable) ()-> {
			try(ServerSocket server = new ServerSocket(port)) {
				System.out.println("Server create");
				while(true) {
					final oneListener mon = new oneListener(mons.size(), server.accept());
					new Thread(mon).start();
					mons.add(mon);
				}
			}catch(final IOException e) {
				e.printStackTrace();
			}
		}, "connect new Client").start();
	}

	private void addMessage(final WebContext message) {
		if(message == null) return;
		new Thread((Runnable) ()-> {
			for(int i = 0; i < mons.size(); i++) if(i != message.getName()) try {
				mons.get(i).sendMessage(message);
			}catch(final SocketException e) {
				synchronized(lock) {
					mons.get(i).close();
					mons.remove(i);
				}
			}catch(final IOException e) {
				e.printStackTrace();
			}catch(final NullPointerException e) {
				mons.remove(i);
			}
		}, "Message write").start();
	}

	public void close() {
		
	}
}
