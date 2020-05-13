package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServeOneClient extends Thread {
	Socket connectionSocket = null;
	int cNumber;
	public ServeOneClient(Socket th, int cn){
		connectionSocket = th;
		cNumber = cn;
	}
	public void run(){
		try {
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort()+'\n');
		} catch (IOException e) {
			System.out.println("  thread " + cNumber + ": something happen in communication with client");
			e.printStackTrace();
		}
	}
}
