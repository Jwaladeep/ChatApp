package chattingApp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class UDPClient {
	public static void main(String s[]) throws Exception {
		DatagramSocket ds;
		// Creating byte array for msg and reply
		byte msg[] = new byte[1024];
		byte reply[] = new byte[1024];
		// Getting Client's host address using Wireless LAN adapter Wi-Fi IPv4 Address
		InetAddress clientAddress = InetAddress.getByName("192.168.1.7"); // abc's address -- 10.52.232.110, piku--
																			// 192.168.20.117
	
		// Getting Server's host address using Wireless LAN adapter Wi-Fi IPv4 Address
		InetAddress serverAddress = InetAddress.getByName("192.168.1.7"); // Jd's Address -- 10.52.234.52

		// creating datapacket to receive msg from Server
		DatagramPacket dpToReceive = new DatagramPacket(msg, msg.length);

		// Creating datapacket to send reply to Server
		DatagramPacket dpToSend = new DatagramPacket(reply, reply.length, serverAddress, 9999);

		try {
			// Creating a socket and binding with specified port of above host
			ds = new DatagramSocket(9999 - 1, clientAddress);

		} catch (Exception e) {
			System.out.print(e);
			return;
		}

		/* reply = null; */

		System.out.print("Enter your msg here: ");
		Scanner sc = new Scanner(System.in);
		String str, replyStr;
		do {

			// Reading msg from Client's Console
			replyStr = sc.nextLine();

			// firstly flush previous data from buffer
			Arrays.fill(reply, (byte) 0);

			// Converting string to byte stream
			reply = replyStr.getBytes();

			// DatagramPacket dp = new DatagramPacket(reply, reply.length, serverAddress,
			// 9999);

			dpToSend.setData(reply);

			// Using serverSocket and sending the packet to Server.
			ds.send(dpToSend);

			// firstly flush previous data from buffer
			Arrays.fill(msg, (byte) 0);

			// DatagramPacket dp2 = new DatagramPacket(msg, msg.length);

			// receiving datapackets in coming to the socket
			ds.receive(dpToReceive);

			// Printing msg and giving space for reply
			str = new String(dpToReceive.getData());
			System.out.print("\nMessage: " + str);
			System.out.print("\nReply: ");

		} while (!replyStr.trim().equalsIgnoreCase("BYE")); // above process is repeated till the client is giving
		// inputs/reply
		sc.close();
		ds.close();

	}
}
