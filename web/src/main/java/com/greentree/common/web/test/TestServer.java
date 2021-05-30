package com.greentree.common.web.test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

public class TestServer {

	//Realtek 8821CE Wireless LAN 802.11ac PCI-E NIC
	private static void findHostInLan() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if(networkInterface.isLoopback() || !networkInterface.isUp())continue;
			System.out.println("begin " + networkInterface.getDisplayName());
			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				InetAddress broadcast = interfaceAddress.getBroadcast();
				if(broadcast != null) log('b', broadcast);
				InetAddress i = interfaceAddress.getAddress();
				if(i != null)log('a', i);
			}
			System.out.println("end");
		}
	}

	private static void log(char c, InetAddress b) {
		System.out.println(c+ " " + b.getHostAddress() + " " + b.getHostName() + " " + b.getCanonicalHostName() + " " + b.getCanonicalHostName() + " " + Arrays.toString(b.getAddress()));
	}

	public static void main(String[] args) throws SocketException {
		findHostInLan();
	}



}
