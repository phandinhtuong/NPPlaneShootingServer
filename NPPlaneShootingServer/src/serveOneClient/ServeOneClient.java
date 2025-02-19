package serveOneClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import main.Main;
import model.Enemy;
import model.Missile;
import model.Player;
import model.Room;
import objectByteTransform.Deserialize;
import objectByteTransform.Serialize;

public class ServeOneClient extends Thread {
	Socket connectionSocket = null;
	int cNumber;
	DataInputStream inFromClient;
	DataOutputStream outToClient;

	public ServeOneClient(Socket th, int cn, DataInputStream in,
			DataOutputStream out) {
		inFromClient = in;
		outToClient = out;
		connectionSocket = th;
		cNumber = cn;
	}

	int numberOfPlayers = Main.numberOfPlayersInOneRoom;
	static int planeWidth = 86;
	static int planeHeight = 84;
	static int enemyWidth = 58;
	static int enemyHeight = 57;
	static int clientFrameWidth = 930;
	static int clientFrameHeight = 992;

	public void run() {
		int i = 0;
		try {
			while ((i = inFromClient.readInt()) != 0) {
				switch (i) {
				case 1: // move plane
					// get room ID
					int roomID = inFromClient.readInt();
					int roomIDInRoomList = indexOfRoomWithID(roomID);
					i = inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					Player planeModelFromClient = Deserialize
							.deserializePlaneModel(planeModelFromClientInByte);
					int playerIDInPlayerListInRoom = indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber);
					synchronized (Main.modelRoomList.get(roomIDInRoomList)
							.getPlayerListInRoom()
							.get(playerIDInPlayerListInRoom)) {

						Main.modelRoomList.get(roomIDInRoomList)
								.getPlayerListInRoom()
								.get(playerIDInPlayerListInRoom)
								.setX(planeModelFromClient.getX());
						Main.modelRoomList.get(roomIDInRoomList)
								.getPlayerListInRoom()
								.get(playerIDInPlayerListInRoom)
								.setY(planeModelFromClient.getY());
					}

					break;
				case 2://launch missile
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					i = inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					Missile missileModelFromClient = Deserialize
							.deserializeMissileModel(missileModelFromClientInByte);
					synchronized (Main.modelRoomList.get(roomIDInRoomList)
							.getMissileList()) {
						Main.modelRoomList.get(roomIDInRoomList)
								.getMissileList().add(missileModelFromClient);
					}

					MissileMove.missileMove(missileModelFromClient,
							roomIDInRoomList);

					break;
				case 3://check victory

					break;
				case 4: // load all players
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);

					byte[] planeModelListInByte = Serialize
							.serializePlaneModelList(roomIDInRoomList);
					outToClient.writeInt(planeModelListInByte.length);
					outToClient.write(planeModelListInByte);
					break;
				case 5:// load all missiles
						// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					byte[] missileModelListInByte = Serialize
							.serializeMissileModelList(roomIDInRoomList);
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);

					break;
				case 6:// load all enemies
						// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					byte[] enemyModelListInByte = Serialize
							.serializeEnemyModelList(roomIDInRoomList);
					outToClient.writeInt(enemyModelListInByte.length);
					outToClient.write(enemyModelListInByte);

					break;
				case 7: // load all rooms
					synchronized (Main.modelRoomList) {
						byte[] roomListInByte = Serialize
								.serializeRoomModelList();
						outToClient.writeInt(roomListInByte.length);
						outToClient.write(roomListInByte);
					}
					Main.ser.displayGameLog("Player "+cNumber+" requested all Rooms");
					break;
				case 8: // create new room
					// players not in any room list
					List<Player> modelPlaneListInRoom = Collections
							.synchronizedList(new ArrayList<Player>());
					List<Missile> modelMissileListInRoom = Collections
							.synchronizedList(new ArrayList<Missile>());
					List<Enemy> modelEnemyListInRoom = Collections
							.synchronizedList(new ArrayList<Enemy>());
					int maxRoomID = getMaxRoomID();
					Room room = new Room(maxRoomID + 1, "room", cNumber,
							modelPlaneListInRoom, modelMissileListInRoom,
							modelEnemyListInRoom, "waiting");
					synchronized (Main.modelRoomList) {
						Main.modelRoomList.add(room);
					}
					// new room id
					Main.ser.displayGameLog("Player "+cNumber+" created Room " + getMaxRoomID());
					outToClient.writeInt(getMaxRoomID());
					break;
				case 9: // player join room
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					if (roomIDInRoomList==-1){
						outToClient.writeInt(2); // room does not exist anymore
					}else
					if (Main.modelRoomList.get(roomIDInRoomList).getStatus()
							.equals("playing")) {
						outToClient.writeInt(0);
					} else {
						outToClient.writeInt(1);
						Main.ser.displayGameLog("Player "+cNumber+" joined Room "
								+ roomIDInRoomList);
						// set waiting state
						Main.modelPlayerListOutsideRoom.get(
								indexOfPlaneWithIDInOutsideList(cNumber))
								.setStatus("waiting");
						// set score
						Main.modelPlayerListOutsideRoom.get(
								indexOfPlaneWithIDInOutsideList(cNumber))
								.setScore(0);

						// add player to player list of room
						Main.modelRoomList
								.get(roomIDInRoomList)
								.getPlayerListInRoom()
								.add(Main.modelPlayerListOutsideRoom
										.get(indexOfPlaneWithIDInOutsideList(cNumber)));
						// remove player from outside list
						Main.modelPlayerListOutsideRoom
								.remove(indexOfPlaneWithIDInOutsideList(cNumber));
						
					}

					break;
				case 10:// remove player from room
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					playerIDInPlayerListInRoom = indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber);
					// set outside state
					Main.modelRoomList.get(roomIDInRoomList)
							.getPlayerListInRoom()
							.get(playerIDInPlayerListInRoom)
							.setStatus("outside");
					// add player to outside list
					Main.modelPlayerListOutsideRoom.add(Main.modelRoomList
							.get(roomIDInRoomList).getPlayerListInRoom()
							.get(playerIDInPlayerListInRoom));
					// remove player from player list of room
					Main.modelRoomList.get(roomIDInRoomList)
							.getPlayerListInRoom()
							.remove(playerIDInPlayerListInRoom);
					Main.ser.displayGameLog("Player "+cNumber+" exited Room "+roomIDInRoomList);
					break;
				case 11: // load all players in room
					// get room ID
					roomID = inFromClient.readInt();
//					 Main.ser.displayGameLog("case 11: roomID " + roomID);
					if (indexOfRoomWithID(roomID) != -1) {
						synchronized (Main.modelRoomList) {
							byte[] playerListInRoomInByte = Serialize
									.serialize(Main.modelRoomList.get(
											indexOfRoomWithID(roomID))
											.getPlayerListInRoom());
							outToClient.writeInt(playerListInRoomInByte.length);
							outToClient.write(playerListInRoomInByte);
						}
					}
					synchronized (Main.modelRoomList) {
						Iterator<Room> it = Main.modelRoomList.iterator();
						while(it.hasNext()){
							Room r = it.next();
							if (r.getPlayerListInRoom().size()==0) it.remove();
						}
					}
					
					break;
				case 12:// set ready state
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					playerIDInPlayerListInRoom = indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber);
					// set ready state
					synchronized (Main.modelRoomList) {
						Main.modelRoomList.get(roomIDInRoomList)
								.getPlayerListInRoom()
								.get(playerIDInPlayerListInRoom)
								.setStatus("ready");
					}
					Main.ser.displayGameLog("Player "+cNumber+" in Room "+roomIDInRoomList+" is ready");
					break;
				case 13: // start game
					// set this player status to playing
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					playerIDInPlayerListInRoom = indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber);
					// set playing state
					Main.modelRoomList.get(roomIDInRoomList)
							.getPlayerListInRoom()
							.get(playerIDInPlayerListInRoom)
							.setStatus("playing");
					if (Main.modelRoomList.get(roomIDInRoomList).getStatus()
							.equals("waiting")) {
						Main.modelRoomList
								.get(roomIDInRoomList)
								.setEnemyList(
										Collections
												.synchronizedList(new ArrayList<Enemy>()));
						Main.modelRoomList
								.get(roomIDInRoomList)
								.setMissileList(
										Collections
												.synchronizedList(new ArrayList<Missile>()));
						Main.modelRoomList.get(roomIDInRoomList).setStatus(
								"playing");
						Main.ser.displayGameLog("Room "+roomIDInRoomList+" started the game");
					}
					
					CreateEnemy.createEnemy(cNumber, roomIDInRoomList);
					break;
				case 14:// player quit game // lose
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					Main.modelRoomList
							.get(roomIDInRoomList)
							.getPlayerListInRoom()
							.get(indexOfPlaneWithIDPlayerListInRoom(
									Main.modelRoomList.get(roomIDInRoomList)
											.getPlayerListInRoom(), cNumber))
							.setStatus("dead");
					break;
				case 15:// set unready state
					// get room ID
					roomID = inFromClient.readInt();
					roomIDInRoomList = indexOfRoomWithID(roomID);
					playerIDInPlayerListInRoom = indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber);
					// set ready state
					synchronized (Main.modelRoomList) {
						Main.modelRoomList.get(roomIDInRoomList)
								.getPlayerListInRoom()
								.get(playerIDInPlayerListInRoom)
								.setStatus("waiting");
					}
					Main.ser.displayGameLog("Player "+cNumber+" in Room "+roomIDInRoomList+" is unready");
					break;

				default:
					break;
				}
				i = 0;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			removePlayerInRoomWithPlayerID(cNumber);
			Main.ser.displayGameLog("connection to client " + cNumber
					+ " closed.");
		}
	}

	static public void removePlayerInRoomWithPlayerID(int ID) {
		// traverse the room list
		for (int i = 0; i < Main.modelRoomList.size(); i++) {
			// traverse the player list
			for (int j = 0; j < Main.modelRoomList.get(i).getPlayerListInRoom()
					.size(); j++) {
				// if find this player
				if (Main.modelRoomList.get(i).getPlayerListInRoom().get(j)
						.getID() == ID) {
					// remove him
					Main.modelRoomList.get(i).getPlayerListInRoom().remove(j);
					// if the player list is empty
					if (Main.modelRoomList.get(i).getPlayerListInRoom().size() == 0) {
						// remove this room
						Main.modelRoomList.remove(i);
						return;
					}
				}
			}

		}
	}

	static public int indexOfPlaneWithIDInOutsideList(int ID) {
		for (int i = 0; i < Main.modelPlayerListOutsideRoom.size(); i++) {
			if (Main.modelPlayerListOutsideRoom.get(i).getID() == ID)
				return i;
		}
		return -1;
	}

	static public int indexOfPlaneWithIDPlayerListInRoom(
			List<Player> playerListInRoom, int playerID) {
		for (int i = 0; i < playerListInRoom.size(); i++) {
			if (playerListInRoom.get(i).getID() == playerID)
				return i;
		}
		return -1;
	}

	static public int indexOfRoomWithID(int ID) {
		for (int i = 0; i < Main.modelRoomList.size(); i++) {
			if (Main.modelRoomList.get(i).getRoomID() == ID)
				return i;
		}
		return -1;
	}

	static public int getMaxRoomID() {
		int max = -1;
		for (int i = 0; i < Main.modelRoomList.size(); i++) {
			if (Main.modelRoomList.get(i).getRoomID() > max)
				max = Main.modelRoomList.get(i).getRoomID();
		}
		return max;
	}

	@SuppressWarnings("unused")
	private static int checkVictory() {
		return 8;
	}

}
