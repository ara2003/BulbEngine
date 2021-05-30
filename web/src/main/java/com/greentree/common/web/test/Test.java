package com.greentree.common.web.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.greentree.common.web.Client;
import com.greentree.common.web.Message;
import com.greentree.common.web.Server;

public class Test {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Server server;
//		server = new Server(7070);
		Client client = new Client("localhost", 7070);

		client.addListener(e -> {
			System.out.println(e.getName() + " " + e.getData());
		});

		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String text = sc.next();
			client.sendMessage(new Message(0, text));
		}

	}

}
