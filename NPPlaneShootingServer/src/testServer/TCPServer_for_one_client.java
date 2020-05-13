package testServer;

import java.io.*;
import java.net.*;
public class TCPServer_for_one_client extends Thread {
	Socket connectionSocket = null;
	int cNumber;
	
	public TCPServer_for_one_client(Socket th, int cn) {
		connectionSocket = th;
		cNumber = cn;
	}
	
	public void run() {
		try {
			System.out.println("  thread " + cNumber + ": Start new thread for serving client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String clientSentence = inFromClient.readLine();
			System.out.println("  thread " + cNumber + ": Received from client: " + clientSentence);
			System.out.println("  thread " + cNumber + ": Send to client: " + clientSentence.toUpperCase());
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
			System.out.println("  thread " + cNumber + ": Finish working with client.\n\n");
		} catch (Exception e) {
			System.out.println("  thread " + cNumber + ": something happen in communication with client");
			e.printStackTrace();
		}
	}
}
