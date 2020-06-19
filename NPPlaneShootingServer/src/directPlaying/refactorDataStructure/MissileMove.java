package directPlaying.refactorDataStructure;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import directPlaying.testOneClient.MissileModel;

public class MissileMove {
	public static void missileMove(final MissileModel missileModel) {
		int delay = 100;
		Server.modelMissileList.get(
				Server.modelMissileList.indexOf(missileModel)).setStatus(
				"launched");
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int missileX = Server.modelMissileList.get(
					Server.modelMissileList.indexOf(missileModel)).getX(); // x
			// does
			// not
			// change
			int missileY = Server.modelMissileList.get(
					Server.modelMissileList.indexOf(missileModel)).getY();
			int enemyPlaneListIndexDie = -1; // dead enemy index
			int k = 0;

			public void actionPerformed(ActionEvent evt) {
				if (Server.modelMissileList
						.get(Server.modelMissileList.indexOf(missileModel))
						.getStatus().equals("dead")) {
					ServerUI.displayGameLog("playerID:"
							+ missileModel.getPlayerID()
							+ " missileID:"
							+ missileModel.getID()
							+ " x:"
							+ Server.modelMissileList.get(
									Server.modelMissileList
											.indexOf(missileModel)).getX()
							+ " y:"
							+ Server.modelMissileList.get(
									Server.modelMissileList
											.indexOf(missileModel)).getY()
							+ " dead");
					// lblMissileList[j][i].setVisible(false);
					synchronized (Server.modelMissileList) {
						Server.modelMissileList.remove(Server.modelMissileList
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
							missileX, missileY, 26, 26);
					if (count == 1080 || missileY < 15
							|| (enemyPlaneListIndexDie != -1)) {
						// missile kills enemy
						if (enemyPlaneListIndexDie != -1) {
							Server.modelMissileList.get(
									Server.modelMissileList
											.indexOf(missileModel)).setStatus(
									"dead");
							Server.modelEnemyList.get(enemyPlaneListIndexDie)
									.setStatus("dead");
							ServerUI.displayGameLog("missileIndex = "
									+ missileModel.getID()
									+ " destroyed enemyPlaneListIndex = "
									+ enemyPlaneListIndexDie);
							enemyPlaneListIndexDie = -1;
						}
						// ServerUI.displayGameLog("missile dead");
						Server.modelMissileList.get(
								Server.modelMissileList.indexOf(missileModel))
								.setStatus("dead");
						// Server.missileModelList.remove(Server.missileModelList.indexOf(missileModel));
						// ((Timer) evt.getSource()).stop();
						// return;
					} else {
						// ServerUI.displayGameLog("missile moving");
						Server.modelMissileList.get(
								Server.modelMissileList.indexOf(missileModel))
								.setStatus("moving");
						Server.modelMissileList.get(
								Server.modelMissileList.indexOf(missileModel))
								.setX(missileX);
						Server.modelMissileList.get(
								Server.modelMissileList.indexOf(missileModel))
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
			int height) {

		for (int i = 0; i < Server.modelEnemyList.size(); i++) {
			if (checkOneCollisionMissileEnemy(x, y, width, height, i)) {
				return i;
			}

		}
		return -1;
	}

	public static boolean checkOneCollisionMissileEnemy(int x, int y,
			int width, int height, int enemyListIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(Server.modelEnemyList.get(enemyListIndex)
				.getX(), Server.modelEnemyList.get(enemyListIndex).getY(),
				ServeOneClient.enemyWidth, ServeOneClient.enemyHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
}
