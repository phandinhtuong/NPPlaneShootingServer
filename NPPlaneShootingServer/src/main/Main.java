package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Player;
import model.Room;
import objectByteTransform.Serialize;

public class Main {
	// number of players in one room
	static int numberOfPlayersInOneRoom = 3;
	// number of enemies each player
	static int numberOfEnemiesEachPlayer = 1000;
	// enemy index of all players in one room
	static int enemyIndexOfAllPlayers = 0;

	// room list
	public static List<Room> modelRoomList = Collections
			.synchronizedList(new ArrayList<Room>());

	// players not in any room list
	public static List<Player> modelPlayerListOutsideRoom = Collections
			.synchronizedList(new ArrayList<Player>());
	// server UI
	static ServerUI ser = new ServerUI();
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		// client index
		int cNumber = 0;

		while (true) {
//			ser.displayGameLog("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept(); // accept
																// connection
																// from client
//			ser.displayGameLog("cNumber = " + cNumber);

			ser.displayGameLog("New connection from : "
					+ connectionSocket.getInetAddress().getHostAddress() + ":"
					+ connectionSocket.getPort());
			ser.displayGameLog("PlayerID : "+cNumber);
			DataInputStream inFromClient = new DataInputStream(
					connectionSocket.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			Player modelPlaneLocal = new Player(cNumber, "outsideRoom", 500,
					500, 0);
			// send client local plane to client
			byte[] planeModelInByte = Serialize.serialize(modelPlaneLocal);
			outToClient.writeInt(planeModelInByte.length); //
			outToClient.write(planeModelInByte);
			modelPlayerListOutsideRoom.add(modelPlaneLocal);

			ServeOneClient serveOneClient = new ServeOneClient(
					connectionSocket, cNumber, inFromClient, outToClient);
			serveOneClient.start();

			cNumber++;
		}
	}
}