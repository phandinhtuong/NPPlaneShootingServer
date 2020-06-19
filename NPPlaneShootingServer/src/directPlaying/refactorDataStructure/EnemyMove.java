package directPlaying.refactorDataStructure;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import testOneClient.EnemyModel;

public class EnemyMove {
	public static void enemyMove(final EnemyModel enemyModel) {
		int delay = 50;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;

			public void actionPerformed(ActionEvent evt) {
				
				if (Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).getStatus()
						.equals("dead")) {
//					ServerUI.displayGameLog("Server.modelEnemyList.size() before = "+Server.modelEnemyList.size());
					synchronized (Server.modelEnemyList) {
						Server.modelEnemyList.remove(Server.modelEnemyList.indexOf(enemyModel));
					}
					
//					ServerUI.displayGameLog("Server.modelEnemyList.size() after = "+Server.modelEnemyList.size());
					((Timer) evt.getSource()).stop();
					return;
				} else {
					enemyPlaneY = 5 * count - ServeOneClient.enemyHeight;// speed
					if ((k = checkCollisionListEnemyPlanes(
							Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).getX(),
							enemyPlaneY, ServeOneClient.enemyWidth,
							ServeOneClient.enemyHeight)) != -1) {
						// update dead player
						Server.modelPlaneList.get(
								ServeOneClient.indexOfPlaneWithID(k))
								.setStatus("dead");
						// ServerUI.displayGameLog("Player "+cNumber+" died!");
						Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).setStatus("dead");
//						((Timer) evt.getSource()).stop();
//						return;

					}
					if (enemyPlaneY >= ServeOneClient.clientFrameHeight) {

						Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).setStatus("dead");
//						((Timer) evt.getSource()).stop();
//						return;
					} else {
						Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).setStatus("moving");
						Server.modelEnemyList.get(Server.modelEnemyList.indexOf(enemyModel)).setY(enemyPlaneY);
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
		for (int i = 0; i < Server.modelPlaneList.size(); i++) {
			if (checkOneCollisionEnemyPlane(x, y, width, height, i))
				return i;
		}
		return -1;
	}

	public static boolean checkOneCollisionEnemyPlane(int x, int y, int width,
			int height, int playerIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Server.modelPlaneList.get(
				ServeOneClient.indexOfPlaneWithID(playerIndex)).getX(),
				Server.modelPlaneList.get(
						ServeOneClient.indexOfPlaneWithID(playerIndex)).getY(),
				ServeOneClient.planeWidth, ServeOneClient.planeHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
}
