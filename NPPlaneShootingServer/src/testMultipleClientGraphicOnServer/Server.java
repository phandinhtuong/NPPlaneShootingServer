package testMultipleClientGraphicOnServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;

public class Server {
	static int numberOfPlayers = 3;
	static int numberOfMissile = 100; //number of missile for each player
	static int numberOfEnemyPlane = 100; //number of enemies for each player
	public static PlaneModel modelPlaneList[] = new PlaneModel[numberOfPlayers];
	public static MissileModel missileModelList[][] = new MissileModel[numberOfPlayers][numberOfMissile];
	public static EnemyModel enemyModelList[][] = new EnemyModel[numberOfPlayers][numberOfEnemyPlane];
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		int cNumber = 0;
		for (int j = 0; j<numberOfPlayers;j++){
			modelPlaneList[j] = new PlaneModel(j,500, 500, "waiting");
			for (int i = 0; i < numberOfMissile; i++) {
				missileModelList[j][i] = new MissileModel(0, 0, 0, 0, "ready");
				enemyModelList[j][i] = new EnemyModel(0, 0, 0,0, "ready");
			}
		}
		@SuppressWarnings("unused")
		ServerUI ser = new ServerUI();
		
		while(true) {
			//System.out.println("TCP Server port: " + tcp_port);
			ServerUI.displayGameLog("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
		//	System.out.println("cNumber = "+cNumber);
			ServerUI.displayGameLog("cNumber = "+cNumber);
			modelPlaneList[cNumber].setStatus("playing");
			//System.out.println("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			ServerUI.displayGameLog("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeInt(cNumber);
			
			ServeOneClient serveOneClient = new ServeOneClient(connectionSocket, cNumber, inFromClient, outToClient);
			serveOneClient.start();
			
			cNumber++;
		}
	}
	
}
