package testServer;

import java.io.*;
import java.net.*;
public class TCPServer_Multi_Thread {
	public static void main(String[] args) throws Exception {
		int tcp_port = 0;
		int cNumber = 1;
		
		if (args.length !=1) {
			System.out.println("Usage: tcpserver [tcp port]");
			System.exit(0);
		}
		try {
			tcp_port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Usage: tcpserver [tcp port]");
			System.exit(0);
		}
		
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		while(true) {
			System.out.println("TCP Server is listening for client connect at port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("  - Got client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			TCPServer_for_one_client server = new TCPServer_for_one_client(connectionSocket, cNumber++);
			server.start();
		}
	}
}
