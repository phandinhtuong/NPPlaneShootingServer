package testMultipleClientGraphicOnServer;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.Timer;

import objectByteTransform.Deserialize;
import objectByteTransform.Serialize;
import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;

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

	int numberOfPlayers = 3;
	int planeWidth = 86;
	int planeHeight = 84;
	int enemyWidth = 58;
	int enemyHeight = 57;
	int numberOfEnemyPlane = 100;
	int clientFrameWidth = 930;
	int clientFrameHeight = 992;
	public void run() {
		int i = 0;
		try {
			createEnemy();
			while ((i = inFromClient.readInt()) != 0) {
				switch (i) {
				case 1:
					i = inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = Deserialize
							.deserializePlaneModel(planeModelFromClientInByte);
					Server.modelPlaneList[planeModelFromClient.getID()]
							.setX(planeModelFromClient.getX());
					Server.modelPlaneList[planeModelFromClient.getID()]
							.setY(planeModelFromClient.getY());

					// System.out.println("client "+planeModelFromClient.getID()+
					// " status: "+planeModelFromClient.getStatus()+" : "+planeModelFromClient.getX()+" "+planeModelFromClient.getY());
//					ServerUI.displayGameLog("client "
//							+ planeModelFromClient.getID() + " status: "
//							+ planeModelFromClient.getStatus() + " : "
//							+ planeModelFromClient.getX() + " "
//							+ planeModelFromClient.getY());
					i = 0;
					break;
				case 2:
					i = inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = Deserialize
							.deserializeMissileModel(missileModelFromClientInByte);
					// System.out.println("client "+missileModelFromClient.getPlayerID()+" missile "+missileModelFromClient.getID()+" "+missileModelFromClient.getX()+" "+missileModelFromClient.getY()+" status "+missileModelFromClient.getStatus());
					ServerUI.displayGameLog("client "
							+ missileModelFromClient.getPlayerID()
							+ " missile " + missileModelFromClient.getID()
							+ " " + missileModelFromClient.getX() + " "
							+ missileModelFromClient.getY() + " status "
							+ missileModelFromClient.getStatus());
					Server.missileModelList[missileModelFromClient
							.getPlayerID()][missileModelFromClient.getID()] = missileModelFromClient;
					launchOneMissile(missileModelFromClient.getPlayerID(),
							missileModelFromClient.getID());
					i = 0;
					break;
				case 3:
					i = inFromClient.readInt();
					byte[] enemyModelFromClientInByte = new byte[i];
					inFromClient.read(enemyModelFromClientInByte);
					EnemyModel enemyModelFromClient = Deserialize
							.deserializeEnemyModel(enemyModelFromClientInByte);
					Server.enemyModelList[enemyModelFromClient.getPlayerID()][enemyModelFromClient
							.getID()] = enemyModelFromClient;
					// System.out.println("enemy "+enemyModelFromClient.getID()+" from client "+enemyModelFromClient.getPlayerID()+" status "+enemyModelFromClient.getStatus());
					ServerUI.displayGameLog("enemy "
							+ enemyModelFromClient.getID() + " from client "
							+ enemyModelFromClient.getPlayerID() + " status "
							+ enemyModelFromClient.getStatus());
					Server.enemyModelList[enemyModelFromClient.getPlayerID()][enemyModelFromClient
							.getID()] = enemyModelFromClient;
					enemyPlaneMove(enemyModelFromClient.getPlayerID(),
							enemyModelFromClient.getID());
					i = 0;
					break;
				case 4:
					byte[] planeModelListInByte = Serialize
							.serialize(Server.modelPlaneList);
					outToClient.writeInt(planeModelListInByte.length);
					outToClient.write(planeModelListInByte);
					// byte[] missileModelListInByte =
					// serialize(Server.missileModelList);
					// outToClient.writeInt(missileModelListInByte.length);
					// outToClient.write(missileModelListInByte);
					// byte[] enemyModelListInByte =
					// serialize(Server.enemyModelList);
					// outToClient.writeInt(enemyModelListInByte.length);
					// outToClient.write(enemyModelListInByte);
					i = 0;
					break;
				case 5:
					byte[] missileModelListInByte = Serialize
							.serialize(Server.missileModelList);
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);
					i = 0;
					break;
				case 6:
					byte[] enemyModelListInByte = Serialize
							.serialize(Server.enemyModelList);
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
			// e.printStackTrace();
			// System.out.println("connection to client "+cNumber+" closed.");
			ServerUI.displayGameLog("connection to client " + cNumber
					+ " closed.");
			Server.modelPlaneList[cNumber].setStatus("disconnected");
			try {
				connectionSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void createEnemy() {
		int delay = 2000;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;

			public void actionPerformed(ActionEvent evt) {
				if (count == numberOfEnemyPlane
						|| Server.modelPlaneList[cNumber].getStatus().equals("dead")) {
					((Timer) evt.getSource()).stop();
				} else {
					Server.enemyModelList[cNumber][count] = new EnemyModel(cNumber, count,
							(int) (Math.random() * (clientFrameWidth
									- enemyWidth - 1)) + 1,-enemyHeight,
							"created");
					enemyPlaneMove(cNumber, count);
				}
				count++;
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
	private void enemyPlaneMove(final int playerID, final int enemyID) {
		int delay = 10;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;

			public void actionPerformed(ActionEvent evt) {
				if (Server.enemyModelList[playerID][enemyID].getStatus()
						.equals("dead")) {
					// lblEnemyList[j][i].setVisible(false);
					((Timer) evt.getSource()).stop();
					return;
				} else {
					enemyPlaneY = count - enemyHeight;// speed
					if ((k = checkCollisionListEnemyPlanes(
							Server.enemyModelList[playerID][enemyID].getX(),
							enemyPlaneY, enemyWidth, enemyHeight)) != -1) {
						// update dead player

						Server.modelPlaneList[k].setStatus("dead");
						ServerUI.displayGameLog("Player "+cNumber+" died!");
						Server.enemyModelList[playerID][enemyID]
								.setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;

					}
					if (enemyPlaneY >= clientFrameHeight// - lblEnemyList[j][i].getHeight()
					) {

						Server.enemyModelList[playerID][enemyID]
								.setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;
					} else {
						Server.enemyModelList[playerID][enemyID]
								.setY(enemyPlaneY);
					}
					count++;
				}
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();

	}

	public int checkCollisionListEnemyPlanes(int x, int y, int width, int height) {
		for (int i = 0; i < numberOfPlayers; i++) {
			if (Server.modelPlaneList[i].getStatus().equals("playing")) {
				if (checkOneCollisionEnemyPlane(x, y, width, height, i))
					return i;
			}
		}
		return -1;
	}

	public boolean checkOneCollisionEnemyPlane(int x, int y, int width,
			int height, int playerIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Server.modelPlaneList[playerIndex].getX(),
				Server.modelPlaneList[playerIndex].getY(), planeWidth,
				planeHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}

	public static void launchOneMissile(final int playerID, final int missileID) {
		int delay = 100;
		Server.missileModelList[playerID][missileID].setStatus("launched");
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int missileX = Server.missileModelList[playerID][missileID].getX(); // x
																				// does
																				// not
																				// change
			int missileY = 0;
			int enemyPlaneListIndexDie = -1; // dead enemy index
			int k = 0;

			public void actionPerformed(ActionEvent evt) {
				if (Server.missileModelList[playerID][missileID].getStatus()
						.equals("dead")) {
					ServerUI.displayGameLog("playerID:"
							+ playerID
							+ " missileID:"
							+ missileID
							+ " x:"
							+ Server.missileModelList[playerID][missileID]
									.getX()
							+ " y:"
							+ Server.missileModelList[playerID][missileID]
									.getY() + " dead");
					// lblMissileList[j][i].setVisible(false);
					((Timer) evt.getSource()).stop();
					return;
				} else {
					// y change with speed
					missileY = Server.missileModelList[playerID][missileID]
							.getY() - 5 * count;
					for (k = 0; k < Server.numberOfPlayers; k++) {
						enemyPlaneListIndexDie = checkCollisionListMissileEnemies(
								missileX, missileY, 26, 26, k);
						if (enemyPlaneListIndexDie != -1)
							break;
					}

					if (count == 1080 || missileY < 15
							|| (enemyPlaneListIndexDie != -1)) {
						// missile kills enemy
						if (enemyPlaneListIndexDie != -1) {
							// lblEnemyList[k][enemyPlaneListIndexDie]
							// .setVisible(false);
							// Server.missileModelList[playerID][missileID].setPlayerID(k);
							// Server.missileModelList[playerID][missileID].setID(enemyPlaneListIndexDie);
							Server.missileModelList[playerID][missileID]
									.setStatus("dead");
							Server.enemyModelList[k][enemyPlaneListIndexDie]
									.setStatus("dead");
							// updateLocalEnemyToServer();
							ServerUI.displayGameLog("missileIndex = "
									+ missileID
									+ " destroyed enemyPlaneListIndex = "
									+ enemyPlaneListIndexDie);
							enemyPlaneListIndexDie = -1;
						}
						// ServerUI.displayGameLog("enemyPlaneListIndexDie = "
						// + enemyPlaneListIndexDie);
						// modelMissileLocal.setPlayerID(j);
						// modelMissileLocal.setID(i);
						// modelMissileLocal.setStatus("dead");
						Server.missileModelList[playerID][missileID]
								.setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;
					} else {
						// lblMissileList[j][i].setVisible(true);
						// lblMissileList[j][i].move(missileX, missileY);
						Server.missileModelList[playerID][missileID]
								.setX(missileX);
						Server.missileModelList[playerID][missileID]
								.setY(missileY);
					}
					count++;
				}
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}

	public static int checkCollisionListMissileEnemies(int x, int y, int width,
			int height, int enemyPlayerIndex) {
		for (int i = 0; i < Server.enemyModelList[enemyPlayerIndex].length; i++) {
			if (Server.enemyModelList[enemyPlayerIndex][i].getStatus().equals(
					"created")) {
				if (checkOneCollisionMissileEnemy(x, y, width, height,
						enemyPlayerIndex, i)) {
					return i;
				}
			}

		}
		return -1;
	}

	public static boolean checkOneCollisionMissileEnemy(int x, int y,
			int width, int height, int enemyPlayerIndex, int enemyListIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(
				Server.enemyModelList[enemyPlayerIndex][enemyListIndex].getX(),
				Server.enemyModelList[enemyPlayerIndex][enemyListIndex].getY(),
				58, 57);
		if (a.intersects(b))
			return true;
		else
			return false;
	}

}
