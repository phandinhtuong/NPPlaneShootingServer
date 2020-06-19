package main;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.*;
import model.RoomList;

public class Main {
	static RoomList roomList = new RoomList();
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		int cNumber = 1;
		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		
//		Player hostPlayer = new Player("as");
//		Room room = new Room("sd", hostPlayer);
//		roomList.add(room);
//		
//		System.out.println(roomList.get(0).getRoomID());
//		byte[] roomByte = Serialize.serialize(roomList);
//		
//		RoomList newRoomList = Deserialize.deserialize(roomByte);
//		
//		System.out.println(newRoomList.get(0).getRoomID());
		
		while(true) {
			System.out.println("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			
			clientSentence = inFromClient.readLine();
			
			System.out.println(clientSentence);
			if (clientSentence.equals("c")){
				NewClientHome newPlayer = new NewClientHome(connectionSocket,cNumber++);
				newPlayer.start();
			}
		}
	}
}