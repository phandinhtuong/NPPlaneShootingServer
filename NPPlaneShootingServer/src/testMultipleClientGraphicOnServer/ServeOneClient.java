package testMultipleClientGraphicOnServer;

import java.awt.Rectangle;
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

	public void run() {
		int i = 0;
		try {
			while ((i = inFromClient.readInt()) != 0) {
				switch (i) {
				case 1:
					i = inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = deserializePlaneModel(planeModelFromClientInByte);
					Server.planeModelList[planeModelFromClient.getID()]
							.setX(planeModelFromClient.getX());
					Server.planeModelList[planeModelFromClient.getID()]
							.setY(planeModelFromClient.getY());

					// System.out.println("client "+planeModelFromClient.getID()+
					// " status: "+planeModelFromClient.getStatus()+" : "+planeModelFromClient.getX()+" "+planeModelFromClient.getY());
					ServerUI.displayGameLog("client "
							+ planeModelFromClient.getID() + " status: "
							+ planeModelFromClient.getStatus() + " : "
							+ planeModelFromClient.getX() + " "
							+ planeModelFromClient.getY());
					i = 0;
					break;
				case 2:
					i = inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = deserializeMissileModel(missileModelFromClientInByte);
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
					EnemyModel enemyModelFromClient = deserializeEnemyModel(enemyModelFromClientInByte);
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
					byte[] planeModelListInByte = serialize(Server.planeModelList);
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
					byte[] missileModelListInByte = serialize(Server.missileModelList);
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);
					i = 0;
					break;
				case 6:
					byte[] enemyModelListInByte = serialize(Server.enemyModelList);
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
			Server.planeModelList[cNumber].setStatus("disconnected");
			try {
				connectionSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void enemyPlaneMove(final int playerID, final int enemyID) {
		int delay = 10;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent evt) {
				if (Server.enemyModelList[playerID][enemyID].getStatus()
						.equals("dead")) {
					// lblEnemyList[j][i].setVisible(false);
					((Timer) evt.getSource()).stop();
					return;
				} else {
					enemyPlaneY = count - 57;// speed
					// if ((k = checkCollisionListEnemyPlanes(
					// enemyModelList[j][i].getX(), enemyPlaneY,
					// lblEnemyList[j][i].getWidth(),
					// lblEnemyList[j][i].getHeight())) != -1) {
					// // update dead player
					// String oldStatus = modelPlaneLocal.getStatus();
					// modelPlaneLocal.setID(k);
					// modelPlaneLocal.setStatus("dead");
					// updateLocalPlaneToServer();
					// modelPlaneLocal.setID(myPlayerID);
					// modelPlaneLocal.setStatus(oldStatus);
					// modelPlaneList[k].setStatus("dead");
					//
					// lblEnemyList[j][i].setVisible(false);
					// modelEnemyLocal.setID(i);
					// modelEnemyLocal.setPlayerID(j);
					// modelEnemyLocal.setStatus("dead");
					// updateLocalEnemyToServer();
					// ((Timer) evt.getSource()).stop();
					// return;
					//
					// }
					// if
					// (checkCollisionListEnemyPlanes(modelEnemyLocal.getX(),
					// enemyPlaneY, lblEnemyList[j][i].getWidth(),
					// lblEnemyList[j][i].getHeight())!=-1) {
					// modelPlaneLocal.setStatus("Died");
					// updateLocalPlaneToServer();
					// // lblPlaneList[myPlayerIndex]
					// // allPlayers[myPlayerIndex].setVisible(false);
					// // TODO
					// lblYouDie.setVisible(true);
					// frame.setCursor(Cursor.DEFAULT_CURSOR);
					// displayGameLog("you ded by enemy plane index " +
					// i
					// + " of player " + j);
					// }

					if (enemyPlaneY >= 1080// - lblEnemyList[j][i].getHeight()
					) {
						// modelEnemyLocal.setPlayerID(j);
						// modelEnemyLocal.setID(i);
						// modelEnemyLocal.setStatus("dead");
						Server.enemyModelList[playerID][enemyID]
								.setStatus("dead");
						// updateLocalEnemyToServer();
						// allEnemies[j][i].setVisible(false);
						((Timer) evt.getSource()).stop();
						return;
					} else {
						// lblEnemyList[j][i].setVisible(true);
						// lblEnemyList[j][i].move(modelEnemyList[j][i].getX(),
						// enemyPlaneY);
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

			@SuppressWarnings("deprecation")
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
//						ServerUI.displayGameLog("enemyPlaneListIndexDie = "
//								+ enemyPlaneListIndexDie);
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

	public static byte[] serialize(PlaneModel[] planeModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(planeModelList);
		return out.toByteArray();
	}

	public static byte[] serialize(PlaneModel planeModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(planeModel);
		return out.toByteArray();
	}

	public static byte[] serialize(MissileModel missileModel)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModel);
		return out.toByteArray();
	}

	public static byte[] serialize(MissileModel[][] missileModelList)
			throws IOException {
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

	public static byte[] serialize(EnemyModel[][] enemyModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModelList);
		return out.toByteArray();
	}

	public static PlaneModel deserializePlaneModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (PlaneModel) is.readObject();
	}

	public static PlaneModel[] deserializePlaneModelList(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (PlaneModel[]) is.readObject();
	}

	public static MissileModel deserializeMissileModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel) is.readObject();
	}

	public static MissileModel[][] deserializeMissileModelList(byte[] data)
			throws IOException, ClassNotFoundException {
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
