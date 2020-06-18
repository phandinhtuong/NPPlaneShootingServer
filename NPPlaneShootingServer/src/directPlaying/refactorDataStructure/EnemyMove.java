package directPlaying.refactorDataStructure;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class EnemyMove {
	public static void enemyMove(final int playerID, final int enemyID) {
		int delay = 10;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int enemyPlaneY = 0;// initial y of enemy
			int k = -1;

			public void actionPerformed(ActionEvent evt) {
				if (Server.enemyModelList[playerID][enemyID].getStatus()
						.equals("dead")) {
					((Timer) evt.getSource()).stop();
					return;
				} else {
					enemyPlaneY = count - ServeOneClient.enemyHeight;// speed
					if ((k = checkCollisionListEnemyPlanes(
							Server.enemyModelList[playerID][enemyID].getX(),
							enemyPlaneY, ServeOneClient.enemyWidth, ServeOneClient.enemyHeight)) != -1) {
						// update dead player
						Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(k)).setStatus("dead");
//						ServerUI.displayGameLog("Player "+cNumber+" died!");
						Server.enemyModelList[playerID][enemyID]
								.setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;

					}
					if (enemyPlaneY >= ServeOneClient.clientFrameHeight
					) {

						Server.enemyModelList[playerID][enemyID]
								.setStatus("dead");
						((Timer) evt.getSource()).stop();
						return;
					} else {
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
	public static int checkCollisionListEnemyPlanes(int x, int y, int width, int height) {
		for (int i = 0; i < Server.numberOfPlayers; i++) {
			if (Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(i)).getStatus().equals("playing")) {
				if (checkOneCollisionEnemyPlane(x, y, width, height, i))
					return i;
			}
		}
		return -1;
	}

	public static boolean checkOneCollisionEnemyPlane(int x, int y, int width,
			int height, int playerIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(playerIndex)).getX(),
				Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(playerIndex)).getY(), ServeOneClient.planeWidth,
				ServeOneClient.planeHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
}
