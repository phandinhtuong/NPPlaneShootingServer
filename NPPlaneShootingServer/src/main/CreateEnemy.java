package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Enemy;

public class CreateEnemy {
	public static void createEnemy(final int cNumber,final int roomIDInRoomList) {
//		final EnemyNumber enemyNumber = new EnemyNumber(cNumber, 0); // new
																		// EnemyNumber
																		// object
																		// to
																		// save
																		// this
																		// player's
																		// enemy
																		// number
//		Main.modelRoomList.get(roomIDInRoomList).getEnemyList().add(enemyNumber); // add to server list of
														// all players
		int delay = 2000;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;

			public void actionPerformed(ActionEvent evt) {
				// if the number of enemy = count or this plane is dead => stop
				if (count == Main.numberOfEnemiesEachPlayer
						|| Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom()
								.get(ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom(), cNumber))
								.getStatus().equals("dead")) {
					((Timer) evt.getSource()).stop();
				} else {
					//new enemy
					Enemy enemyModel = new Enemy(
							
							count,
							(int) (Math.random() * (ServeOneClient.clientFrameWidth
									- ServeOneClient.enemyWidth - 1)) + 1,
							-ServeOneClient.enemyHeight, "created");
					// ServerUI.displayGameLog("enemy created");
					
//					Server.enemyListOfAllPlayers.get(
//							Server.enemyListOfAllPlayers.indexOf(enemyNumber))
//							.setEnemyID(count + 1);// update enemy index
//					Server.enemyIndexOfAllPlayers = Server.enemyIndexOfAllPlayers + 1;
					synchronized (Main.modelRoomList.get(roomIDInRoomList).getEnemyList()) {
						Main.modelRoomList.get(roomIDInRoomList).getEnemyList().add(enemyModel);
					}
					EnemyMove.enemyMove(Main.modelRoomList.get(roomIDInRoomList).getEnemyList()
							.get(Main.modelRoomList.get(roomIDInRoomList).getEnemyList().indexOf(enemyModel)),roomIDInRoomList);
				}
				count++;
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
}
