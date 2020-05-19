package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.RoomList;

public class Server {
	static RoomList roomList = new RoomList();
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		int cNumber = 1;
		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		
		while(true) {
			System.out.println("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);
			if (clientSentence.equals("c")){
				NewClientHome newPlayer = new NewClientHome(connectionSocket,cNumber++);
				newPlayer.start();
			}
//			else if(clientSentence.equals("r")){
//				CreateNewRoom newRoom = new CreateNewRoom(connectionSocket,cNumber++);
//			}
			
		}
	}
}
