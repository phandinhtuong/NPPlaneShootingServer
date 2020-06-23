package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Enemy;

public class CreateEnemy {
	static Enemy enemyModel = null;

	public static void createEnemy(final int cNumber, final int roomIDInRoomList) {
		int delay = 1000;
		// create enemy every 1000 milliseconds
		ActionListener taskPerformer = new ActionListener() {
			int count = 0; // count enemy number
			int speed = 0; // speed of the enemy

			public void actionPerformed(ActionEvent evt) {
				if (roomIDInRoomList < Main.modelRoomList.size()) {
					if (ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(
							Main.modelRoomList.get(roomIDInRoomList)
									.getPlayerListInRoom(), cNumber) == -1) {
						((Timer) evt.getSource()).stop();
					}
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
						if (speed % 2 == 0) { // only create enemy when speed is
												// even
							// new enemy
							enemyModel = new Enemy(
									count,
									(int) (Math.random() * (ServeOneClient.clientFrameWidth
											- ServeOneClient.enemyWidth - 1)) + 1,
									-ServeOneClient.enemyHeight, "created");
							synchronized (Main.modelRoomList.get(
									roomIDInRoomList).getEnemyList()) {
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
									roomIDInRoomList, cNumber, speed * 2);
						}
					}
					count++;
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
