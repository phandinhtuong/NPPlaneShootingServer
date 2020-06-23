package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Missile;

public class MissileMove {
	public static void missileMove(final Missile missileModel, final int roomIDInRoomList) {
		int delay = 100;
		Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
				Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel)).setStatus(
				"launched");
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int missileX = Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
					Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel)).getX(); // x
			// does
			// not
			// change
			int missileY = Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
					Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel)).getY();
			int enemyPlaneListIndexDie = -1; // dead enemy index
			int k = 0;

			public void actionPerformed(ActionEvent evt) {
				if (Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
						.getStatus().equals("dead")) {
					synchronized (Main.modelRoomList.get(roomIDInRoomList).getMissileList()) {
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().remove(Main.modelRoomList.get(roomIDInRoomList).getMissileList()
								.indexOf(missileModel));
					}
					
					((Timer) evt.getSource()).stop();
					return;
				} else {
					// y change with speed
					missileY = missileY - 2 * count;
					// TODO
					// for (k = 0; k < Server.numberOfPlayers; k++) {
					// enemyPlaneListIndexDie =
					// checkCollisionListMissileEnemies(
					// missileX, missileY, 26, 26, k);
					// if (enemyPlaneListIndexDie != -1)
					// break;
					// }
					enemyPlaneListIndexDie = checkCollisionListMissileEnemies(
							missileX, missileY, 26, 26,roomIDInRoomList);
					if (count == 1080 || missileY < 15
							|| (enemyPlaneListIndexDie != -1)) {
						// missile kills enemy
						if (enemyPlaneListIndexDie != -1) {
							//add one score to plane
							addOneScoreToPlane(missileModel.getPlayerID(),roomIDInRoomList);
							
							//update the enemy to dead
							Main.modelRoomList.get(roomIDInRoomList).getEnemyList().get(enemyPlaneListIndexDie)
									.setStatus("dead");
							//display log
							//set dead enemy index to -1
							enemyPlaneListIndexDie = -1;
						}
						// ServerUI.displayGameLog("missile dead");
						//update this missile to dead
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
								Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
								.setStatus("dead");
						// Server.missileModelList.remove(Server.missileModelList.indexOf(missileModel));
						// ((Timer) evt.getSource()).stop();
						// return;
					} else {
						// ServerUI.displayGameLog("missile moving");
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
								Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
								.setStatus("moving");
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
								Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
								.setX(missileX);
						Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
								Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
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
			int height,int roomIDInRoomList) {

		for (int i = 0; i < Main.modelRoomList.get(roomIDInRoomList).getEnemyList().size(); i++) {
			if (checkOneCollisionMissileEnemy(x, y, width, height, i,roomIDInRoomList)) {
				return i;
			}

		}
		return -1;
	}

	public static boolean checkOneCollisionMissileEnemy(int x, int y,
			int width, int height, int enemyListIndex,int roomIDInRoomList) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Main.modelRoomList.get(roomIDInRoomList).getEnemyList().get(enemyListIndex)
				.getX(), Main.modelRoomList.get(roomIDInRoomList).getEnemyList().get(enemyListIndex).getY(),
				ServeOneClient.enemyWidth, ServeOneClient.enemyHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
	public static void addOneScoreToPlane(int playerID, int roomID){
		synchronized (Main.modelRoomList.get(roomID).getPlayerListInRoom()) {
			for (int i=0;i<Main.modelRoomList.get(roomID).getPlayerListInRoom().size();i++){
				if (Main.modelRoomList.get(roomID).getPlayerListInRoom().get(i).getID()==playerID) Main.modelRoomList.get(roomID).getPlayerListInRoom().get(i).addOneScore();
			}
		}
	}
}
