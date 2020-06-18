package directPlaying.refactorDataStructure;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import testOneClient.MissileModel;

public class LaunchMissile {
	public static void launchMissile(final MissileModel missileModel) {
		int delay = 100;
		Server.missileModelList.get(Server.missileModelList.indexOf(missileModel)).setStatus("launched");
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;
			int missileX = Server.missileModelList.get(Server.missileModelList.indexOf(missileModel)).getX(); // x
																				// does
																				// not
																				// change
			int missileY = Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
					.getY();
			int enemyPlaneListIndexDie = -1; // dead enemy index
			int k = 0;

			public void actionPerformed(ActionEvent evt) {
				if (Server.missileModelList.get(Server.missileModelList.indexOf(missileModel)).getStatus()
						.equals("dead")) {
					ServerUI.displayGameLog("playerID:"
							+ missileModel.getPlayerID()
							+ " missileID:"
							+ missileModel.getID()
							+ " x:"
							+ Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
									.getX()
							+ " y:"
							+ Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
									.getY() + " dead");
					// lblMissileList[j][i].setVisible(false);
					Server.missileModelList.remove(Server.missileModelList.indexOf(missileModel));
					((Timer) evt.getSource()).stop();
					return;
				} else {
					// y change with speed
					missileY = missileY - 2 * count;
					//TODO
//					for (k = 0; k < Server.numberOfPlayers; k++) {
//						enemyPlaneListIndexDie = checkCollisionListMissileEnemies(
//								missileX, missileY, 26, 26, k);
//						if (enemyPlaneListIndexDie != -1)
//							break;
//					}

					if (count == 1080 || missileY < 15
							|| (enemyPlaneListIndexDie != -1)) {
						// missile kills enemy
						if (enemyPlaneListIndexDie != -1) {
							Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
									.setStatus("dead");
							Server.enemyModelList[k][enemyPlaneListIndexDie]
									.setStatus("dead");
							ServerUI.displayGameLog("missileIndex = "
									+ missileModel.getID()
									+ " destroyed enemyPlaneListIndex = "
									+ enemyPlaneListIndexDie);
							enemyPlaneListIndexDie = -1;
						}
						ServerUI.displayGameLog("missile dead");
						Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
								.setStatus("dead");
//						Server.missileModelList.remove(Server.missileModelList.indexOf(missileModel));
//						((Timer) evt.getSource()).stop();
//						return;
					} else {
						ServerUI.displayGameLog("missile moving");
						Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
						.setStatus("moving");
						Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
								.setX(missileX);
						Server.missileModelList.get(Server.missileModelList.indexOf(missileModel))
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
			int height, int enemyPlayerIndex) {
		for (int i = 0; i < Server.enemyModelList[enemyPlayerIndex].length; i++) {
			if (Server.enemyModelList[enemyPlayerIndex][i].getStatus().equals(
					"created")) {
				if (checkOneCollisionMissileEnemy(x, y, width, height,
						enemyPlayerIndex, i)) {
					return i;
				}
			}

		}
		return -1;
	}
	public static boolean checkOneCollisionMissileEnemy(int x, int y,
			int width, int height, int enemyPlayerIndex, int enemyListIndex) {
		Rectangle a = new Rectangle(x, y, width, height);
		Rectangle b = new Rectangle(
				Server.enemyModelList[enemyPlayerIndex][enemyListIndex].getX(),
				Server.enemyModelList[enemyPlayerIndex][enemyListIndex].getY(),
				ServeOneClient.enemyWidth, ServeOneClient.enemyHeight);
		if (a.intersects(b))
			return true;
		else
			return false;
	}
}
