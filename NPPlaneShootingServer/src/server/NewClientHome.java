package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import model.Player;

public class NewClientHome extends Thread {
	Socket connectionSocket = null;
	int cNumber;
	String clientSentence;
	public NewClientHome(Socket th, int cn){
		connectionSocket = th;
		cNumber = cn;
	}
	public void run(){
		try {
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			String playerID = connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort();
			Player player = new Player(playerID);
			outToClient.writeBytes(playerID+'\n');
			//TODO display current player
			while((clientSentence = inFromClient.readLine())!=null){
				
				if(clientSentence.equals("r")){
					System.out.println(clientSentence);
					CreateNewRoom newRoom = new CreateNewRoom(player,connectionSocket,inFromClient,outToClient,cNumber++);
					newRoom.start();
					break;
				}
				
				if(clientSentence.equals("l")){
					System.out.println(clientSentence);
					ListRoom listRoom = new ListRoom(outToClient);
					listRoom.start();
					break;
					
					
				}
			}
		} catch (IOException e) {
			System.out.println("NewClientHome  thread " + cNumber + ": something happen in communication with client");
			e.printStackTrace();
		}
	}
}
