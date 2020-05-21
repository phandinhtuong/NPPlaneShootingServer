package testOneClient;

import java.io.Serializable;

public class EnemyModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int playerID;
	int ID;
	int x;
	String status;
	public EnemyModel(int playerID, int ID, int x,String status) {
		this.playerID = playerID;
		this.ID = ID;
		this.x = x;
		this.status = status;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
