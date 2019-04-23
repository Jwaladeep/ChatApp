package chattingApp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class UDPServer {

	public static void main(String st[]) throws Exception {
		DatagramSocket ds;
		// Creating byte array for msg and reply
		byte msg[] = new byte[1024];
		byte reply[] = new byte[1024];

		// Getting Client's host address using Wireless LAN adapter Wi-Fi IPv4 Address
		InetAddress clientAddress = InetAddress.getByName("10.52.232.110"); // Afsar's address -- 10.52.232.110, piku--
																			// 192.168.20.117

		// Getting Server's host address using Wireless LAN adapter Wi-Fi IPv4 Address
		InetAddress serverAddress = InetAddress.getByName("10.52.234.52"); // Jd's Address -- 10.52.234.52

		// creating datapacket to receive msg from Client
		DatagramPacket dpToReceive = new DatagramPacket(msg, msg.length);

		// Creating datapacket to send reply to Client
		DatagramPacket dpToSend = new DatagramPacket(reply, reply.length, clientAddress, 9999 - 1);

		Scanner sc = new Scanner(System.in);
		try {

			// Creating a socket and binding with specified port of above host
			ds = new DatagramSocket(9999, serverAddress);

		} catch (Exception e) { // while creating socket , it can throw UnknownException or IOException thus
								// handling

			System.out.println(e);
			sc.close();
			return;
		}

		System.out.print("Waiting for Call from Client");
		String str;
		String replyStr;
		do {

			// firstly flush previous data from buffer
			Arrays.fill(msg, (byte) 0);

			// Receiving data packets in the socket
			ds.receive(dpToReceive);

			// Taking byte array from datapacket and converting to string
			str = new String(dpToReceive.getData());

			// Printing data from Client
			System.out.print("\nMessage: " + str);

			// Giving space to Server to write msg
			System.out.print("\nReply: ");

			// Reading msg from Console
			replyStr = sc.nextLine();

			// firstly flush previous data from buffer
			Arrays.fill(reply, (byte) 0);

			// Converting string to byte stream
			reply = replyStr.getBytes();

			// setting reply in the datapacket
			dpToSend.setData(reply);

			// Using serverSocket and sending the packet to Client.
			ds.send(dpToSend);

		} while (!replyStr.trim().equalsIgnoreCase("exit")); // Above steps will be repeated till the time user is
																// giving inputs through
		// console

		// Closing all the resources
		sc.close();
		ds.close();

	}
}
