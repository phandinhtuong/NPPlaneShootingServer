package directPlaying.refactorDataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import testOneClient.EnemyModel;

public class CreateEnemy {
	public void createEnemy(final int cNumber) {
		int delay = 2000;
		ActionListener taskPerformer = new ActionListener() {
			int count = 0;

			public void actionPerformed(ActionEvent evt) {
				if (count == Server.numberOfEnemyPlane
						|| Server.modelPlaneList.get(ServeOneClient.indexOfPlaneWithID(cNumber)).getStatus().equals("dead")) {
					((Timer) evt.getSource()).stop();
				} else {
					Server.enemyModelList[cNumber][count] = new EnemyModel(cNumber, count,
							(int) (Math.random() * (ServeOneClient.clientFrameWidth
									- ServeOneClient.enemyWidth - 1)) + 1,-ServeOneClient.enemyHeight,
							"created");
					EnemyMove.enemyMove(cNumber, count);
				}
				count++;
			}
		};
		Timer t = new Timer(delay, taskPerformer);
		t.start();
	}
}
