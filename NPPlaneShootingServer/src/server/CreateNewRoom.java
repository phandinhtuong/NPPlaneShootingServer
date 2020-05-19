package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.Player;
import model.Room;

public class CreateNewRoom extends Thread{
	Socket connectionSocket = null;
	int cNumber;
	BufferedReader inFromClient;
	DataOutputStream outToClient;
	Player player;
	public CreateNewRoom(Player p,Socket th,BufferedReader in,DataOutputStream out, int cn){
		connectionSocket = th;
		cNumber = cn;
		inFromClient = in;
		outToClient = out;
		player = p;
	}
	public void run(){
		try {
			String newRoomID = connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort();
			//DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(newRoomID+'\n');
			Room newRoom = new Room(newRoomID, player);
			Server.roomList.add(newRoom);
		} catch (IOException e) {
			System.out.println("  thread " + cNumber + ": something happen in communication with client");
			e.printStackTrace();
		}
	}
}
