package testServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		String capitalizedSentence; 
		int clientPort;
		ServerSocket serverSocket = new ServerSocket(6789);
		while (true) {
			Socket connectionSocket = serverSocket.accept();
			clientPort = connectionSocket.getPort();
			System.out.println("New connection from "+ clientPort+".");
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Sentence from client "+clientPort+" : "+clientSentence+".");
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
			//connectionSocket.
			if (connectionSocket.isOutputShutdown())
				System.out.println("Connection from client "+clientPort+" closed.");
			
		}

	}

}
