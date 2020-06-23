package main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Enemy;

public class EnemyMove {
	public static void enemyMove(final Enemy enemyModel, final int roomID) {
		int delay = 50;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;

			public void actionPerformed(ActionEvent evt) {
				
				if (Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).getStatus()
						.equals("dead")) {
//					ServerUI.displayGameLog("Server.modelEnemyList.size() before = "+Server.modelEnemyList.size());
					synchronized (Main.modelRoomList.get(roomID).getEnemyList()) {
						Main.modelRoomList.get(roomID).getEnemyList().remove(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel));
					}
					
//					ServerUI.displayGameLog("Server.modelEnemyList.size() after = "+Server.modelEnemyList.size());
					((Timer) evt.getSource()).stop();
					return;
				} else {
					enemyPlaneY = 5 * count - ServeOneClient.enemyHeight;// speed
					if ((k = checkCollisionListEnemyPlanes(
							Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).getX(),
							enemyPlaneY, ServeOneClient.enemyWidth,
							ServeOneClient.enemyHeight,roomID)) != -1) {
						// update dead player
						Main.modelRoomList.get(roomID).getPlayerListInRoom().get(k)
								.setStatus("dead");
						// ServerUI.displayGameLog("Player "+cNumber+" died!");
						//update enemy dead
						Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("dead");
//						((Timer) evt.getSource()).stop();
//						return;

					}
					if (enemyPlaneY >= ServeOneClient.clientFrameHeight) {

						Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("dead");
//						((Timer) evt.getSource()).stop();
//						return;
					} else {
						Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setStatus("moving");
						Main.modelRoomList.get(roomID).getEnemyList().get(Main.modelRoomList.get(roomID).getEnemyList().indexOf(enemyModel)).setY(enemyPlaneY);
					}
					count++;
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
//		Rectangle b = new Rectangle(Main.modelRoomList.get(roomID).getPlayerListInRoom().get(
//				ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(Main.modelRoomList.get(roomID).getPlayerListInRoom(), playerIndex)).getX(),
//				Main.modelRoomList.get(roomID).getPlayerListInRoom().get(
//						ServeOneClient.indexOfPlaneWithIDPlayerListInRoom(Main.modelRoomList.get(roomID).getPlayerListInRoom(), playerIndex)).getY(),
//				ServeOneClient.planeWidth, ServeOneClient.planeHeight);
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
