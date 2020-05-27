package testMultipleClientGraphicOnServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.Timer;

import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;

public class ServeOneClient extends Thread{
	Socket connectionSocket = null;
	int cNumber;
	DataInputStream inFromClient;
	DataOutputStream outToClient;
	public ServeOneClient(Socket th, int cn,DataInputStream in,
	DataOutputStream out){
		inFromClient = in;
		outToClient = out;
		connectionSocket = th;
		cNumber = cn;
	}
	public void run(){
		int i = 0;
		try {
			while((i=inFromClient.readInt())!=0){
				switch (i) {
				case 1:
					i=inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = deserializePlaneModel(planeModelFromClientInByte);
					Server.planeModelList[planeModelFromClient.getID()].setX(planeModelFromClient.getX());
					Server.planeModelList[planeModelFromClient.getID()].setY(planeModelFromClient.getY());
					
					//System.out.println("client "+planeModelFromClient.getID()+ " status: "+planeModelFromClient.getStatus()+" : "+planeModelFromClient.getX()+" "+planeModelFromClient.getY());
					ServerUI.displayGameLog("client "+planeModelFromClient.getID()+ " status: "+planeModelFromClient.getStatus()+" : "+planeModelFromClient.getX()+" "+planeModelFromClient.getY());
					i = 0;
					break;
				case 2:
					i=inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = deserializeMissileModel(missileModelFromClientInByte);
				//	System.out.println("client "+missileModelFromClient.getPlayerID()+" missile "+missileModelFromClient.getID()+" "+missileModelFromClient.getX()+" "+missileModelFromClient.getY()+" status "+missileModelFromClient.getStatus());
					ServerUI.displayGameLog("client "+missileModelFromClient.getPlayerID()+" missile "+missileModelFromClient.getID()+" "+missileModelFromClient.getX()+" "+missileModelFromClient.getY()+" status "+missileModelFromClient.getStatus());
					Server.missileModelList[missileModelFromClient.getPlayerID()][missileModelFromClient.getID()] = missileModelFromClient;
					launchOneMissile(missileModelFromClient.getPlayerID(),missileModelFromClient.getID());
					i = 0;
					break;
				case 3:
					i=inFromClient.readInt();
					byte[] enemyModelFromClientInByte = new byte[i];
					inFromClient.read(enemyModelFromClientInByte);
					EnemyModel enemyModelFromClient = deserializeEnemyModel(enemyModelFromClientInByte);
					Server.enemyModelList[enemyModelFromClient.getPlayerID()][enemyModelFromClient.getID()] = enemyModelFromClient;
					//System.out.println("enemy "+enemyModelFromClient.getID()+" from client "+enemyModelFromClient.getPlayerID()+" status "+enemyModelFromClient.getStatus());
					ServerUI.displayGameLog("enemy "+enemyModelFromClient.getID()+" from client "+enemyModelFromClient.getPlayerID()+" status "+enemyModelFromClient.getStatus());
					i = 0;
					break;
				case 4:
					byte[] planeModelListInByte = serialize(Server.planeModelList);
					outToClient.writeInt(planeModelListInByte.length);
					outToClient.write(planeModelListInByte);
//					byte[] missileModelListInByte = serialize(Server.missileModelList);
//					outToClient.writeInt(missileModelListInByte.length);
//					outToClient.write(missileModelListInByte);
//					byte[] enemyModelListInByte = serialize(Server.enemyModelList);
//					outToClient.writeInt(enemyModelListInByte.length);
//					outToClient.write(enemyModelListInByte);
					i = 0;
					break;
				case 5:
					byte[] missileModelListInByte = serialize(Server.missileModelList);
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);
					i=0;
					break;
				default:
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println("connection to client "+cNumber+" closed.");
			ServerUI.displayGameLog("connection to client "+cNumber+" closed.");
			Server.planeModelList[cNumber].setStatus("disconnected");
			try {
				connectionSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void launchOneMissile(final int playerID, final int missileID){
		int delay = 50;
		Server.missileModelList[playerID][missileID].setStatus("launched");
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int missileX = Server.missileModelList[playerID][missileID].getX(); // x does not change
			int missileY = 0;
			int enemyPlaneListIndexDie = -1; // dead enemy index
			int k = 0;
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent evt) {
				if (Server.missileModelList[playerID][missileID].getStatus().equals("dead")) {
					ServerUI.displayGameLog("playerID:" + playerID + " missileID:" + missileID + " x:"
							+ Server.missileModelList[playerID][missileID].getX() + " y:"
							+ Server.missileModelList[playerID][missileID].getY() + " dead");
					//lblMissileList[j][i].setVisible(false);
					((Timer) evt.getSource()).stop();
					return;
				} else {
					// y change with speed
					missileY = Server.missileModelList[playerID][missileID].getY() - 15 * count;
					for (k = 0; k < Server.numberOfPlayers; k++) {
//						enemyPlaneListIndexDie = checkCollisionListMissileEnemies(
//								missileX, missileY,
//								26,
//								26, k);
						if (enemyPlaneListIndexDie != -1)
							break;
					}

					if (count == 1080 || missileY < 15
							|| (enemyPlaneListIndexDie != -1)) {
						// missile kills enemy
						if (enemyPlaneListIndexDie != -1) {
//							lblEnemyList[k][enemyPlaneListIndexDie]
//									.setVisible(false);
//							Server.missileModelList[playerID][missileID].setPlayerID(k);
//							Server.missileModelList[playerID][missileID].setID(enemyPlaneListIndexDie);
							Server.missileModelList[playerID][missileID].setStatus("dead");
				//TODO			modelEnemyList[k][enemyPlaneListIndexDie]
//									.setStatus("dead");
						//	updateLocalEnemyToServer();
							ServerUI.displayGameLog("missileIndex = " + missileID
									+ " destroyed enemyPlaneListIndex = "
									+ enemyPlaneListIndexDie);
							enemyPlaneListIndexDie = -1;
						}
						ServerUI.displayGameLog("enemyPlaneListIndexDie = "
								+ enemyPlaneListIndexDie);
//						modelMissileLocal.setPlayerID(j);
//						modelMissileLocal.setID(i);
//						modelMissileLocal.setStatus("dead");
						Server.missileModelList[playerID][missileID].setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;
					} else {
						//lblMissileList[j][i].setVisible(true);
						//lblMissileList[j][i].move(missileX, missileY);
						Server.missileModelList[playerID][missileID].setX(missileX);
						Server.missileModelList[playerID][missileID].setY(missileY);
					}
					count++;
				}
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
	public static void checkCollision(){
		
	}
	public static byte[] serialize(PlaneModel[] planeModelList) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(planeModelList);
	    return out.toByteArray();
	}
	public static byte[] serialize(PlaneModel planeModel) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(planeModel);
	    return out.toByteArray();
	}
	public static byte[] serialize(MissileModel missileModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModel);
		return out.toByteArray();
	}
	public static byte[] serialize(MissileModel[][] missileModelList) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModelList);
		return out.toByteArray();
	}
	public static byte[] serialize(EnemyModel enemyModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModel);
		return out.toByteArray();
	}
	public static byte[] serialize(EnemyModel[][] enemyModelList) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModelList);
		return out.toByteArray();
	}
	public static PlaneModel deserializePlaneModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (PlaneModel) is.readObject();
	}
	public static PlaneModel[] deserializePlaneModelList(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (PlaneModel[]) is.readObject();
	}
	public static MissileModel deserializeMissileModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel) is.readObject();
	}
	public static MissileModel[][] deserializeMissileModelList(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel[][]) is.readObject();
	}
	public static EnemyModel deserializeEnemyModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (EnemyModel) is.readObject();
	}

}
