package server;

import java.net.ServerSocket;
import java.net.Socket;

import testServer.TCPServer_for_one_client;

public class Server {
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		int cNumber = 1;
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		while(true) {
			System.out.println("TCP Server is listening for client connect at port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("  - Got client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			ServeOneClient server = new ServeOneClient(connectionSocket, cNumber++);
			server.start();
		}
	}
}
