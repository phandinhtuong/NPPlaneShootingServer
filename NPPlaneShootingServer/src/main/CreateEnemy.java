package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Enemy;

public class CreateEnemy {
	static Enemy enemyModel = null;
	public static void createEnemy(final int cNumber, final int roomIDInRoomList) {
		// final EnemyNumber enemyNumber = new EnemyNumber(cNumber, 0); // new
		// EnemyNumber
		// object
		// to
		// save
		// this
		// player's
		// enemy
		// number
		// Main.modelRoomList.get(roomIDInRoomList).getEnemyList().add(enemyNumber);
		// // add to server list of
		// all players
		int delay = 1000;
		
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int speed = 0;

			public void actionPerformed(ActionEvent evt) {
				if (roomIDInRoomList < Main.modelRoomList.size()) {
					// Main.ser.displayGameLog("s");
					// Main.ser.displayGameLog("cNumber "+cNumber);
					// Main.ser.displayGameLog("Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom().size() "+Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom().size());
					// if
					// (cNumber<Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom().size()
					// &&
					// ServeOneClient
					// .indexOfPlaneWithIDPlayerListInRoom(
					// Main.modelRoomList
					// .get(roomIDInRoomList)
					// .getPlayerListInRoom(),
					// cNumber)!=-1
					// ){
					// if this player quit the game
					if (ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber) == -1) {
						((Timer) evt.getSource()).stop();
					}
					// if the number of enemy = count or this plane is dead =>
					// stop
					if (count == Main.numberOfEnemiesEachPlayer
							|| Main.modelRoomList
									.get(roomIDInRoomList)
									.getPlayerListInRoom()
									.get(ServeOneClient
											.indexOfPlaneWithIDPlayerListInRoom(
													Main.modelRoomList
															.get(roomIDInRoomList)
															.getPlayerListInRoom(),
													cNumber)).getStatus()
									.equals("dead")) {
						((Timer) evt.getSource()).stop();
					} else {
						
						speed = count / 10;
						speed = (speed + 1);
//						speed = (speed + 1) * 2;
//						Main.ser.displayGameLog("speed = " + speed);
//						Main.ser.displayGameLog("ID = " + count);
						if(speed%2==0){ // only create enemy when speed is even
							// new enemy
							enemyModel = new Enemy(
									count,
									(int) (Math.random() * (ServeOneClient.clientFrameWidth
											- ServeOneClient.enemyWidth - 1)) + 1,
									-ServeOneClient.enemyHeight, "created");
							synchronized (Main.modelRoomList.get(roomIDInRoomList)
									.getEnemyList()) {
								Main.modelRoomList.get(roomIDInRoomList)
										.getEnemyList().add(enemyModel);
							}

							
							EnemyMove.enemyMove(
									Main.modelRoomList
											.get(roomIDInRoomList)
											.getEnemyList()
											.get(Main.modelRoomList
													.get(roomIDInRoomList)
													.getEnemyList()
													.indexOf(enemyModel)),
									roomIDInRoomList, cNumber, speed*2);
						}
						
						
						// ServerUI.displayGameLog("enemy created");

						// Server.enemyListOfAllPlayers.get(
						// Server.enemyListOfAllPlayers.indexOf(enemyNumber))
						// .setEnemyID(count + 1);// update enemy index
						// Server.enemyIndexOfAllPlayers =
						// Server.enemyIndexOfAllPlayers + 1;
						
					}
					count++;
					// }

				} else {
					((Timer) evt.getSource()).stop();
					return;
				}

			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
}
