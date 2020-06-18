package directPlaying.refactorDataStructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import objectByteTransform.Serialize;
import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;

public class Server {
	static int numberOfPlayers = 3;
	static int numberOfMissile = 100; //number of missile for each player
	static int numberOfEnemyPlane = 100; //number of enemies for each player
	
	static ArrayList<PlaneModel> modelPlaneList = new ArrayList<PlaneModel>();
//	public static PlaneModel modelPlaneList[] = new PlaneModel[numberOfPlayers];
	
	
//	public static MissileModel missileModelList[][] = new MissileModel[numberOfPlayers][numberOfMissile];
	static ArrayList<MissileModel> missileModelList = new ArrayList<MissileModel>();
	
	
	public static EnemyModel enemyModelList[][] = new EnemyModel[numberOfPlayers][numberOfEnemyPlane];
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		int cNumber = 0;
//		for (int j = 0; j<numberOfPlayers;j++){
//			
//			//Initial modelPlaneList
////			modelPlaneList[j] = new PlaneModel(j,500, 500, "waiting");
//			
//			
//			for (int i = 0; i < numberOfMissile; i++) {
//				missileModelList[j][i] = new MissileModel(0, 0, 0, 0, "ready");
//				enemyModelList[j][i] = new EnemyModel(0, 0, 0,0, "ready");
//			}
//		}
		@SuppressWarnings("unused")
		ServerUI ser = new ServerUI();
		
		while(true) {
			ServerUI.displayGameLog("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept(); // accept connection from client
			ServerUI.displayGameLog("cNumber = "+cNumber);
			
			
//			planeModel = new PlaneModel(cNumber, 500, 500, "playing");
//			modelPlaneList.add(planeModel); // add this plane to plane list
//			modelPlaneList[cNumber].setStatus("playing"); //set this player status playing
			
			ServerUI.displayGameLog("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//			int i = 0;
			
			PlaneModel modelPlaneLocal = new PlaneModel(cNumber, 500, 500, "playing"); //Initialize client local plane
			
			
			
			
			//send client local plane to client
			byte[] planeModelInByte = Serialize.serialize(modelPlaneLocal);
			outToClient.writeInt(planeModelInByte.length); //
			outToClient.write(planeModelInByte);
			modelPlaneList.add(modelPlaneLocal);
			
//			while ((i = inFromClient.readInt()) != 0) {
//				i = inFromClient.readInt();
//				byte[] planeModelFromClientInByte = new byte[i];
//				inFromClient.read(planeModelFromClientInByte);
//				PlaneModel planeModelFromClient = Deserialize
//						.deserializePlaneModel(planeModelFromClientInByte);
//				ServerUI.displayGameLog("cNumber = "+cNumber);
//				planeModelFromClient.setID(cNumber);
//				modelPlaneList.add(planeModelFromClient);
//				break;
//			}
//			outToClient.writeInt(cNumber);
			
			ServeOneClient serveOneClient = new ServeOneClient(connectionSocket, cNumber, inFromClient, outToClient);
			serveOneClient.start();
			
			cNumber++;
		}
	}
	
}
