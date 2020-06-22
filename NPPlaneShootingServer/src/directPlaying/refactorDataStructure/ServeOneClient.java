package directPlaying.refactorDataStructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import directPlaying.testOneClient.EnemyModel;
import directPlaying.testOneClient.MissileModel;
import directPlaying.testOneClient.PlaneModel;
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

	int numberOfPlayers = Server.numberOfPlayers;
	static int planeWidth = 86;
	static int planeHeight = 84;
	static int enemyWidth = 58;
	static int enemyHeight = 57;
	int numberOfEnemyPlane = 100;
	static int clientFrameWidth = 930;
	static int clientFrameHeight = 992;
	public void run() {
		int i = 0;
		try {
			
			CreateEnemy.createEnemy(cNumber);
			while ((i = inFromClient.readInt()) != 0) {
				switch (i) {
				case 1:
					i = inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = Deserialize
							.deserializePlaneModel(planeModelFromClientInByte);
					
					synchronized (Server.modelPlaneList) {
						Server.modelPlaneList.get(planeModelFromClient.getID()).setX(planeModelFromClient.getX());
						Server.modelPlaneList.get(planeModelFromClient.getID()).setY(planeModelFromClient.getY());
					}
					
					
					
//					Server.modelPlaneList[planeModelFromClient.getID()]
//							.setX(planeModelFromClient.getX());
//					Server.modelPlaneList[planeModelFromClient.getID()]
//							.setY(planeModelFromClient.getY());
					i = 0;
					break;
				case 2:
					i = inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = Deserialize
							.deserializeMissileModel(missileModelFromClientInByte);
					ServerUI.displayGameLog("client "
							+ missileModelFromClient.getPlayerID()
							+ " missile " + missileModelFromClient.getID()
							+ " " + missileModelFromClient.getX() + " "
							+ missileModelFromClient.getY() + " status "
							+ missileModelFromClient.getStatus());
					
					synchronized (Server.modelMissileList) {
						Server.modelMissileList.add(missileModelFromClient);
					}
					
					
//					synchronized /(missileModelFromClient) {
//					}
					MissileMove.missileMove(missileModelFromClient);
					i = 0;
					break;
				case 3: 
					outToClient.writeInt(checkVictory()); 
					i = 0; 
					break;
				case 4:
					byte[] planeModelListInByte = Serialize
							.serializePlaneModelList();
					outToClient.writeInt(planeModelListInByte.length);
					outToClient.write(planeModelListInByte);
					i = 0;
					break;
				case 5:
					byte[] missileModelListInByte = Serialize
							.serializeMissileModelList();
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);
					i = 0;
					break;
				case 6:
					byte[] enemyModelListInByte = Serialize
							.serializeEnemyModelList();
					outToClient.writeInt(enemyModelListInByte.length);
					outToClient.write(enemyModelListInByte);
					i = 0;
					break;
				default:
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			ServerUI.displayGameLog("connection to client " + cNumber
					+ " closed.");
//			Server.modelPlaneList[cNumber].setStatus("disconnected");
			Server.modelPlaneList.get(indexOfPlaneWithID(cNumber)).setStatus("disconnected");
			try {
				connectionSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	static public int indexOfPlaneWithID(int ID){
		for (PlaneModel planeModelInList : Server.modelPlaneList){
			if (planeModelInList.getID() == ID){
				return ID;
			}
		}
		return -1;
	}
	private static int checkVictory(){
//		int count = 0;
//		for (int i = 0; i<Server.enemyListOfAllPlayers.size();i++){
//			if (Server.enemyListOfAllPlayers.get(i).getEnemyID() == Server.numberOfEnemiesEachPlayer){
//				count++;
//			}
//		}
//		if (count == Server.enemyListOfAllPlayers.size() && Server.modelEnemyList.size()==0) return 1;
//		else return 2;
		if (Server.enemyIndexOfAllPlayers == Server.modelPlaneList.size() * Server.numberOfEnemiesEachPlayer && Server.modelEnemyList.size()==0){
			return -1;
		} else return Server.modelPlaneList.size() * Server.numberOfEnemiesEachPlayer - Server.enemyIndexOfAllPlayers + Server.modelEnemyList.size();
//		for (int i = 0;i<Server.enemyListOfAllPlayers.size();i++){
//			
//		}
//		return Server.enemyListOfAllPlayers.get(0).getEnemyID();
//		return count;
//		return Server.enemyListOfAllPlayers.size();
	}
	
	
	
	

}
