package directPlaying.testOneClient;

public class EnemyNumber {
	int playerID;
	int enemyID;
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getEnemyID() {
		return enemyID;
	}
	public void setEnemyID(int enemyID) {
		this.enemyID = enemyID;
	}
	public EnemyNumber(int playerID, int enemyID) {
		super();
		this.playerID = playerID;
		this.enemyID = enemyID;
	}
	
}
