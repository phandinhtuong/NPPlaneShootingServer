package main;

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
							ServeOneClient.enemyHeight)) != -1) {
						// update dead player
//						Server.modelPlaneList.get(
//								ServeOneClient.indexOfPlaneWithID(k))
//								.setStatus("dead");
						// ServerUI.displayGameLog("Player "+cNumber+" died!");
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

	// public static int checkCollisionListEnemyPlanes(int x, int y, int width,
	// int height) {
	// for (int i = 0; i < Server.numberOfPlayers; i++) {
	// if
	// (Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(i)).getStatus().equals("playing"))
	// {
	// if (checkOneCollisionEnemyPlane(x, y, width, height, i))
	// return i;
	// }
	// }
	// return -1;
	// }
	public static int checkCollisionListEnemyPlanes(int x, int y, int width,
			int height) {
//		for (int i = 0; i < Server.modelPlaneList.size(); i++) {
//			if (checkOneCollisionEnemyPlane(x, y, width, height, i))
//				return i;
//		}
		return -1;
	}

	public static boolean checkOneCollisionEnemyPlane(int x, int y, int width,
			int height, int playerIndex) {
//		Rectangle a = new Rectangle(x, y, width, height);
//		Rectangle b = new Rectangle(Server.modelPlaneList.get(
//				ServeOneClient.indexOfPlaneWithID(playerIndex)).getX(),
//				Server.modelPlaneList.get(
//						ServeOneClient.indexOfPlaneWithID(playerIndex)).getY(),
//				ServeOneClient.planeWidth, ServeOneClient.planeHeight);
//		if (a.intersects(b))
//			return true;
//		else
			return false;
	}
}
