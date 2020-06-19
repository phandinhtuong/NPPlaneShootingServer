package directPlaying.refactorDataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import directPlaying.testOneClient.EnemyModel;
import directPlaying.testOneClient.EnemyNumber;

public class CreateEnemy {
	public static void createEnemy(final int cNumber) {
		final EnemyNumber enemyNumber = new EnemyNumber(cNumber, 0); // new
																		// EnemyNumber
																		// object
																		// to
																		// save
																		// this
																		// player's
																		// enemy
																		// number
		Server.enemyListOfAllPlayers.add(enemyNumber); // add to server list of
														// all players
		int delay = 2000;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;

			public void actionPerformed(ActionEvent evt) {
				if (count == Server.numberOfEnemiesEachPlayer
						|| Server.modelPlaneList
								.get(ServeOneClient.indexOfPlaneWithID(cNumber))
								.getStatus().equals("dead")) {
					((Timer) evt.getSource()).stop();
				} else {
					EnemyModel enemyModel = new EnemyModel(
							cNumber,
							count,
							(int) (Math.random() * (ServeOneClient.clientFrameWidth
									- ServeOneClient.enemyWidth - 1)) + 1,
							-ServeOneClient.enemyHeight, "created");
					// ServerUI.displayGameLog("enemy created");
					Server.enemyListOfAllPlayers.get(
							Server.enemyListOfAllPlayers.indexOf(enemyNumber))
							.setEnemyID(count + 1);// update enemy index
					Server.enemyIndexOfAllPlayers = Server.enemyIndexOfAllPlayers + 1;
					synchronized (Server.modelEnemyList) {
						Server.modelEnemyList.add(enemyModel);
					}
					EnemyMove.enemyMove(Server.modelEnemyList
							.get(Server.modelEnemyList.indexOf(enemyModel)));
				}
				count++;
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
}
