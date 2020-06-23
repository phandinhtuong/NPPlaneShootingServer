package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Enemy;

public class EnemyMove {
	public static void enemyMove(final Enemy enemyModel, final int roomID,final int cNumber,final int speed) {
		int delay = 50;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;
			public void actionPerformed(ActionEvent evt) {
				if (roomID < Main.modelRoomList.size()){
					//if this player quit the game
					if (ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(Main.modelRoomList.get(roomID).getPlayerListInRoom(), cNumber)==-1){
						((Timer) evt.getSource()).stop();
						return;
					}
					//if this is not a new list
					if(Main.modelRoomList.get(roomID).getEnemyList().size()!=0){
						if (Main.modelRoomList
								.get(roomID)
								.getEnemyList()
								.get(Main.modelRoomList.get(roomID).getEnemyList()
										.indexOf(enemyModel)).getStatus()
								.equals("dead")) {
							synchronized (Main.modelRoomList.get(roomID).getEnemyList()) {
								Main.modelRoomList.get(roomID).getEnemyList().remove(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel));
							}
							((Timer) evt.getSource()).stop();
							return;
						} else {
							enemyPlaneY = speed * count - ServeOneClient.enemyHeight;// speed
							if ((k = checkCollisionListEnemyPlanes(
									Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).getX(),
									enemyPlaneY, ServeOneClient.enemyWidth,
									ServeOneClient.enemyHeight,roomID)) != -1) {
								// update dead player
								Main.modelRoomList.get(roomID).getPlayerListInRoom().get(k)
										.setStatus("dead");
								//update enemy dead
								Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("dead");
							}
							if (enemyPlaneY >= ServeOneClient.clientFrameHeight) {

								Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("dead");
							} else {
								Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("moving");
								Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setY(enemyPlaneY);
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

	//check if this enemy collides any planes in list -- return the index of collision plane
	public static int checkCollisionListEnemyPlanes(int x, int y, int width,
			int height, int roomID) {
		for (int i = 0; i < Main.modelRoomList.get(roomID).getPlayerListInRoom().size(); i++) {
			if (checkOneCollisionEnemyPlane(x, y, width, height, i,roomID))
				return i;
		}
		return -1;
	}

	//check if this enemy collides this plane
	public static boolean checkOneCollisionEnemyPlane(int x, int y, int width,
			int height, int playerIndex,int roomID) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Main.modelRoomList.get(roomID).getPlayerListInRoom().get(
				playerIndex).getX(),
				Main.modelRoomList.get(roomID).getPlayerListInRoom().get(
						playerIndex).getY(),
				ServeOneClient.planeWidth, ServeOneClient.planeHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
}
