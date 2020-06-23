package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Missile;

public class MissileMove {
	public static void missileMove(final Missile missileModel, final int roomIDInRoomList) {
		int delay = 50;
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

			public void actionPerformed(ActionEvent evt) {
				if (roomIDInRoomList < Main.modelRoomList.size()){
					//if this is not a new list
					if(Main.modelRoomList.get(roomIDInRoomList).getMissileList().size()!=0&&Main.modelRoomList.get(roomIDInRoomList).getMissileList().size()!=0){
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
							missileY = missileY - 15;
							//check if this missile collides any enemy in enemy list
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
								//update this missile to dead
								Main.modelRoomList.get(roomIDInRoomList).getMissileList().get(
										Main.modelRoomList.get(roomIDInRoomList).getMissileList().indexOf(missileModel))
										.setStatus("dead");
							} else {
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
					}else{
						((Timer) evt.getSource()).stop();
						return;
					}
				}else{
					((Timer) evt.getSource()).stop();
					return;
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
